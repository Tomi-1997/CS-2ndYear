
using MongoDB.Driver;
using MongoDB.Bson;

using BusinessEntities;
using BusinessLogic;
using System.Collections;
namespace MyMongoAccess
{
    class MyMongoAccess
    {
        static string connStr = "mongodb+srv://simha_reef:[password here]@cluster0.lkwjn6f.mongodb.net/test";

        public static void createTables()
        {

            var settings = MongoClientSettings.FromConnectionString(connStr);
            settings.ServerApi = new ServerApi(ServerApiVersion.V1);
            var client = new MongoClient(settings);
            var database = client.GetDatabase("Shop");

            database.DropCollection("Flavors");
            database.CreateCollection("Flavors");

            database.DropCollection("Additives");
            database.CreateCollection("Additives");

            database.DropCollection("Containers");
            database.CreateCollection("Containers");

            database.DropCollection("Orders");
            database.CreateCollection("Orders");

            // var dbList = database.ListCollectionNames().ToList();
            // Console.WriteLine("The list of databases on this server is: ");
            // foreach (var db in dbList)
            // {
            //     Console.WriteLine(db);
            // }
        }

        public static void insertObject(Object obj)
        {
            var settings = MongoClientSettings.FromConnectionString(connStr);
            settings.ServerApi = new ServerApi(ServerApiVersion.V1);
            var client = new MongoClient(settings);
            var database = client.GetDatabase("Shop");

            if (obj is Flavor)
            {
                var flav = database.GetCollection<BsonDocument>("Flavors");
                var f = (Flavor) obj;
                var doc = new BsonDocument { {"Name", f.getName()}, {"Price", f.getPrice()}};
                flav.InsertOne(doc);
            }

            if (obj is Container)
            {
                var cont = database.GetCollection<BsonDocument>("Containers");
                var c = (Container) obj;
                var doc = new BsonDocument {{"Name", c.getName()} , {"Price", c.getPrice()}};
                cont.InsertOne(doc);
            }

            if (obj is Additive)
            {
                var adtv = database.GetCollection<BsonDocument>("Additives");
                var a = (Additive) obj;
                var doc = new BsonDocument {{"Name", a.getName()}, {"Price", a.getPrice()}};
                adtv.InsertOne(doc);
            }
        }

        public static int addOrder()
        {
            var settings = MongoClientSettings.FromConnectionString(connStr);
            settings.ServerApi = new ServerApi(ServerApiVersion.V1);
            var client = new MongoClient(settings);
            var database = client.GetDatabase("Shop");
            var orders = database.GetCollection<BsonDocument>("Orders");

            long objectId = orders.CountDocuments(new BsonDocument());
            var doc = new BsonDocument {{"myId", objectId},{"Flavors", new BsonArray()}, {"Container", " "}, {"Additive", " "}, {"Completed", false}, {"Sum", 0}, {"Date", DateTime.Now}};
            orders.InsertOne(doc);
            return (int)objectId;
        }

        public static ArrayList readAll(string tableName)
        {
            ArrayList all = new ArrayList();
            var settings = MongoClientSettings.FromConnectionString(connStr);
            settings.ServerApi = new ServerApi(ServerApiVersion.V1);
            var client = new MongoClient(settings);
            var database = client.GetDatabase("Shop");
            var table = database.GetCollection<BsonDocument>(tableName);

            var docs = table.Find(new BsonDocument()).ToList();
            int id = 1;
            foreach (BsonDocument doc in docs)
                {
                    // int id = Array.IndexOf(Logic.CONTAINERS, Convert.ToString(doc["Name"]));
                    all.Add(new Object[] {id, (doc["Name"]), Convert.ToInt16(doc["Price"])});
                    id++;
                }
            return all;
        }

        public static void updateContainer(int id, int container)
        {
            int id_in_array = container - 1;
            try
            {
                var settings = MongoClientSettings.FromConnectionString(connStr);
                settings.ServerApi = new ServerApi(ServerApiVersion.V1);
                var client = new MongoClient(settings);
                var database = client.GetDatabase("Shop");
                var orders = database.GetCollection<BsonDocument>("Orders");

                var filter = Builders<BsonDocument>.Filter.Eq("myId", id);
                var update = Builders<BsonDocument>.Update.Set("Container", Logic.CONTAINERS[id_in_array]);
                orders.UpdateOne(filter, update);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }   
        }

        public static void addOrderScoop(int orderId, int flavId)
        {
            int flav_array = flavId - 1;
            try
            {
                var settings = MongoClientSettings.FromConnectionString(connStr);
                settings.ServerApi = new ServerApi(ServerApiVersion.V1);
                var client = new MongoClient(settings);
                var database = client.GetDatabase("Shop");
                var orders = database.GetCollection<BsonDocument>("Orders");

                var filter = Builders<BsonDocument>.Filter.Eq("myId", orderId);
                var update = Builders<BsonDocument>.Update.Push("Flavors", Logic.FLAVORS[flav_array]);
                orders.UpdateOne(filter, update);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }   
        }

        public static ArrayList getScoops(int id)
        {
            ArrayList ans = new ArrayList();
            try
            {
                var settings = MongoClientSettings.FromConnectionString(connStr);
                settings.ServerApi = new ServerApi(ServerApiVersion.V1);
                var client = new MongoClient(settings);
                var database = client.GetDatabase("Shop");
                var orders = database.GetCollection<BsonDocument>("Orders");

                var filter = Builders<BsonDocument>.Filter.Eq("myId", id);
                var doc = orders.Find(filter).First();
                BsonArray flavs = (BsonArray) doc["Flavors"];
                foreach (string flav in flavs)
                {
                    ans.Add(flav);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }   
            return ans;
        }

        public static string getAdditiveName(int id)
        {
            return Logic.ADDITIVES[id - 1];
        }

        public static void updateOrderAdditive(int id, int addtv)
        {
            string additive = getAdditiveName(addtv);
            try
            {
                var settings = MongoClientSettings.FromConnectionString(connStr);
                settings.ServerApi = new ServerApi(ServerApiVersion.V1);
                var client = new MongoClient(settings);
                var database = client.GetDatabase("Shop");
                var orders = database.GetCollection<BsonDocument>("Orders");

                var filter = Builders<BsonDocument>.Filter.Eq("myId", id);
                var update = Builders<BsonDocument>.Update.Set("Additive", additive);
                orders.UpdateOne(filter, update);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }   

        }

        public static int getContainerPrice(string container)
        {
            int ans = 0;
            try
            {
                var settings = MongoClientSettings.FromConnectionString(connStr);
                settings.ServerApi = new ServerApi(ServerApiVersion.V1);
                var client = new MongoClient(settings);
                var database = client.GetDatabase("Shop");
                var orders = database.GetCollection<BsonDocument>("Containers");

                var filter = Builders<BsonDocument>.Filter.Eq("Name", container);
                var doc = orders.Find(filter).First();
                ans = (int)doc["Price"];
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
            Console.Write("Price for a " + container + " is " + ans +", ");
            return ans;
        }

        public static int getAdditivePrice(string additive)
        {
            int ans = 0;
            try
            {
                var settings = MongoClientSettings.FromConnectionString(connStr);
                settings.ServerApi = new ServerApi(ServerApiVersion.V1);
                var client = new MongoClient(settings);
                var database = client.GetDatabase("Shop");
                var orders = database.GetCollection<BsonDocument>("Additives");

                var filter = Builders<BsonDocument>.Filter.Eq("Name", additive);
                var doc = orders.Find(filter).First();
                ans = (int)doc["Price"];
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
            Console.Write("price for the " + additive + " is " + ans +", ");
            return ans;
        }

        public static int getScoopsPrice(ArrayList scoops)
        {
            int p = 0;
            int count = scoops.Count;
            int atStart = count;
            while (count > 0)
                    {
                        if (count > 3)
                        {
                            p += 6;
                            count--;
                            continue;
                        }
                        if (count == 3)
                        {
                            p += 18;
                            count = 0;
                            break;
                        }
                        if (count == 2)
                        {
                            p += 12;
                            count = 0;
                            break;
                        }
                        if (count == 1)
                        {
                            p += 7;
                            count = 0;
                            break;
                        }
                    }
            Console.Write("price for " + atStart +" scoops is " + p + ".\n");
            return p;
        }

        public static int calcSum(int id)
        {
            int ans = 0;
            try
            {
                var settings = MongoClientSettings.FromConnectionString(connStr);
                settings.ServerApi = new ServerApi(ServerApiVersion.V1);
                var client = new MongoClient(settings);
                var database = client.GetDatabase("Shop");
                var orders = database.GetCollection<BsonDocument>("Orders");

                var filter = Builders<BsonDocument>.Filter.Eq("myId", id);
                var doc = orders.Find(filter).First();

                ans += getContainerPrice((string)doc["Container"]);

                if ((string)doc["Additive"] != " ")
                    ans += getAdditivePrice((string)doc["Additive"]);

                ans += getScoopsPrice(getScoops(id));
                Console.WriteLine("Total of " + ans +".");
                var update = Builders<BsonDocument>.Update.Set("Sum", ans);
                orders.UpdateOne(filter, update);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }   

            return ans;
        }

        public static int addSale(int id)
        {
            try
            {
                var settings = MongoClientSettings.FromConnectionString(connStr);
                settings.ServerApi = new ServerApi(ServerApiVersion.V1);
                var client = new MongoClient(settings);
                var database = client.GetDatabase("Shop");
                var orders = database.GetCollection<BsonDocument>("Orders");

                var filter = Builders<BsonDocument>.Filter.Eq("myId", id);
                var update = Builders<BsonDocument>.Update.Set("Completed", true);
                orders.UpdateOne(filter, update);

                int sum = calcSum(id);

                update = Builders<BsonDocument>.Update.Set("Sum", sum);
                orders.UpdateOne(filter, update);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }   
            return id;
        }
    }
}
