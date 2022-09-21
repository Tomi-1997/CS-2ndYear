using System.Collections;
using BusinessEntities;

namespace BusinessLogic
{
    public class Logic
    {

        public static string[] FLAVORS = { "Vanilla", "Strawberry", "Chocolate", "Mint Chocolate Chip" , 
        "Coffee", "Pistachio", "Hazelnut", "Lychee", "Lemon", "Mekupelet" };
        public static string[] ADDITIVES = {"Hot chocolate", "Peanuts", "Maple"};
        public static string[] CONTAINERS = {"Box", "Cone", "Special Cone"};
        public static int[] CNTR_LIM = {Int16.MaxValue, 3, 3};

        public static int adtvNum()
        {
            return ADDITIVES.Count();
        }

        public static int flavNum()
        {
            return FLAVORS.Count();
        }
        static Dictionary<string,string> SCOOP_BLOCK = new Dictionary<string, string>
        {
            // Which type of scoop blocks which type of additive.
            // Will add info to INFO dictionary based on code below.
            {"Chocolate", "Hot chocolate"},
            {"Mekupelet", "Hot chocolate"},
            {"Vanilla", "Maple"},
        };
        static Dictionary<string,string> INFO = new Dictionary<string, string>
        {
            // Will print info to the customer if it exsits in this dictionary.
            {"Box", "unlimited ice cream scoops."},
            {"Cone", "up to 3 ice cream scoops, additive only for 2 or 3 scoops."},
            {"Special Cone", "up to 3 ice cream scoops, no scoop minimum for an additive."},
        };

        public static void updateInfo()
        {
            foreach(KeyValuePair<string, string> entry in SCOOP_BLOCK)
            {
                INFO.Add(entry.Key, "incompatible with the " + SCOOP_BLOCK[entry.Key] + " additive.");
            }
        }

        public static void createTables()
        {
            if (MODE.CURR == MODE.SQL)
                createTablesSQL();
            if (MODE.CURR == MODE.MONGO)
                createTablesMONGO();
        }

        public static void createTablesSQL()
        {
            MySqlAccess.MySqlAccess.createTables();
            foreach (string flav in FLAVORS)
            {
                MySqlAccess.MySqlAccess.insertObject(new Flavor(flav, 0));
            }

            foreach (string addtv in ADDITIVES)
            {
                MySqlAccess.MySqlAccess.insertObject(new Additive(addtv, 2));
            }

            // BOX                                         ARGS:    //NAME--PRICE--SCOOP LIM, MIN SCOOP FOR ADDITIVE
            MySqlAccess.MySqlAccess.insertObject(new Container(CONTAINERS[0], 5,    -1,           0, INFO["Box"]));
            // CONE
            MySqlAccess.MySqlAccess.insertObject(new Container(CONTAINERS[1], 0,     3,           2, INFO["Cone"]));
            // SPECIAL CONE
            MySqlAccess.MySqlAccess.insertObject(new Container(CONTAINERS[2], 2,     3,           0, INFO["Special Cone"]));
        
        }

        public static void createTablesMONGO()
        {
            MyMongoAccess.MyMongoAccess.createTables();
            foreach (string flav in FLAVORS)
            {
                MyMongoAccess.MyMongoAccess.insertObject(new Flavor(flav, 0));
            }

            foreach (string addtv in ADDITIVES)
            {
                MyMongoAccess.MyMongoAccess.insertObject(new Additive(addtv, 2));
            }

            // BOX                                         ARGS:    //NAME--PRICE--SCOOP LIM, MIN SCOOP FOR ADDITIVE
            MyMongoAccess.MyMongoAccess.insertObject(new Container(CONTAINERS[0], 5,    -1,           0, INFO["Box"]));
            // CONE
            MyMongoAccess.MyMongoAccess.insertObject(new Container(CONTAINERS[1], 0,     3,           2, INFO["Cone"]));
            // SPECIAL CONE
            MyMongoAccess.MyMongoAccess.insertObject(new Container(CONTAINERS[2], 2,     3,           0, INFO["Special Cone"]));
        }

        public static Boolean isBlocking(string scoop, string additive)
        {
            return SCOOP_BLOCK.Where(x => x.Key == scoop && x.Value == additive)
            .ToDictionary(x => x.Key, x => x.Value).Count > 0;
        }

        public static void printTableData(string tableName)
        {
            ArrayList all = new ArrayList();

            if (MODE.CURR == MODE.SQL)
            {
                all = MySqlAccess.MySqlAccess.readAll(tableName);
            }
            if (MODE.CURR == MODE.MONGO)
            {
                all = MyMongoAccess.MyMongoAccess.readAll(tableName);
            }
            Console.WriteLine("ID___NAME___PRICE___INFO\n");
            foreach (Object[] row in all)
            {
                string info = "";
                string? name = Convert.ToString(row[1]);
                if ( !(name is null) && INFO.ContainsKey(name))
                {
                    info = ", " + INFO[name];
                }
        

                foreach (var v in row)
                {
                    Console.Write(v+"  ");
                }
                Console.Write(info);
                Console.WriteLine();
            }
            Console.WriteLine("________________________");
        }

    }
}