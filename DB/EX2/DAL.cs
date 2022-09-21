using MySql.Data.MySqlClient;
using BusinessEntities;
using System.Collections;

namespace MySqlAccess
{
    class MySqlAccess
    {

        public static string connStr = "server=localhost;user=root;port=3306;password=1234";
        
        /*
        this call will represent CRUD operation
        CRUD stands for Create,Read,Update,Delete
        */
        public static void createTables()
        {
            try
            {
                MySqlConnection conn = new MySqlConnection(connStr);
                conn.Open();
                string sql = "DROP DATABASE IF EXISTS Shop;";

                MySqlCommand cmd = new MySqlCommand(sql, conn);
                cmd.ExecuteNonQuery();

                sql = "CREATE DATABASE Shop;";
                cmd = new MySqlCommand(sql, conn);
                cmd.ExecuteNonQuery();

                // create flavors table
                sql = "CREATE TABLE `Shop`.`Flavors` (" +
                    "`idFlavor` INT NOT NULL AUTO_INCREMENT, " +
                    "`Name` VARCHAR(45) NOT NULL," +
                    "`Price` INT NULL," +
                    "PRIMARY KEY (`idFlavor`));";

                cmd = new MySqlCommand(sql, conn);
                cmd.ExecuteNonQuery();

                // create additives table
                sql = "CREATE TABLE `Shop`.`Additives` (" +
                    "`idAdditive` INT NOT NULL AUTO_INCREMENT, " +
                    "`Name` VARCHAR(45) NOT NULL," +
                    "`Price` INT NULL," +
                    "PRIMARY KEY (`idAdditive`));";

                cmd = new MySqlCommand(sql, conn);
                cmd.ExecuteNonQuery();

                // create containers table
                sql = "CREATE TABLE `Shop`.`Containers` (" +
                    "`idContainers` INT NOT NULL AUTO_INCREMENT, " +
                    "`Name` VARCHAR(45) NOT NULL," +
                    "`Price` INT NULL," +
                    "PRIMARY KEY (`idContainers`));";

                cmd = new MySqlCommand(sql, conn);
                cmd.ExecuteNonQuery();

                // create Orders
                sql = "CREATE TABLE `Shop`.`Orders` (" +
                    "`idOrder` INT NOT NULL AUTO_INCREMENT, " +
                    "`ContainerType` INT NULL," +
                    "`Additive` INT NULL," +
                    "PRIMARY KEY (`idOrder`));";

                cmd = new MySqlCommand(sql, conn);
                cmd.ExecuteNonQuery();

                // create OrderScoops, linked to Orders by orderID
                sql = "CREATE TABLE `Shop`.`OrderScoops` (" +
                    "`idOrder` INT NOT NULL, " +
                    "`idScoop` INT NOT NULL, " +
                    "FOREIGN KEY (idOrder) REFERENCES Orders(idOrder));";

                cmd = new MySqlCommand(sql, conn);
                cmd.ExecuteNonQuery();

                // Sales
                sql = "CREATE TABLE `Shop`.`Sales` (" +
                    "`idSale` INT NOT NULL AUTO_INCREMENT, " +
                    "`Date` DATETIME NOT NULL," +
                    "`Sum` INT NOT NULL," +
                    "`idOrder` INT NOT NULL," +
                    "PRIMARY KEY (`idSale`));";

                cmd = new MySqlCommand(sql, conn);
                cmd.ExecuteNonQuery();
                conn.Close();

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
        }

        // Begins a new order and returns the id
        public static int addOrder()
        {
            int id = -1;
            try
            {
                MySqlConnection conn = new MySqlConnection(connStr);
                conn.Open();

                string sql = "INSERT INTO `Shop`.`Orders` (`ContainerType`, `Additive`) " +
                "VALUES ('" + -1 + "', '" + -1 + "');";

                MySqlCommand cmd = new MySqlCommand(sql, conn);
                cmd.ExecuteNonQuery();
                id = (int)cmd.LastInsertedId;

                conn.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
            return id;
        }

        public static void addOrderScoop(int orderId, int flavId)
        {
            try
            {
                MySqlConnection conn = new MySqlConnection(connStr);
                conn.Open();

                string sql = "INSERT INTO `Shop`.`OrderScoops` (`idOrder`, `idScoop`) " +
                "VALUES ('" + orderId + "', '" + flavId + "');";
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                cmd.ExecuteNonQuery();
                conn.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
        }

        public static void updateOrderAdditive(int id, int addtv)
        {
            try
            {
            MySqlConnection conn = new MySqlConnection(connStr);
            conn.Open();
            MySqlCommand cmd = new MySqlCommand();
            cmd.CommandText = "UPDATE Shop.Orders SET Additive = @addtv WHERE idOrder = @id";
            cmd.Parameters.AddWithValue("@addtv",addtv);
            cmd.Parameters.AddWithValue("@id",id);
            cmd.Connection = conn;
            cmd.ExecuteNonQuery();
            conn.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }     
        }

        public static void insertObject(Object obj)
        {
            try
            {
                MySqlConnection conn = new MySqlConnection(connStr);
                // Console.WriteLine("Connecting to MySQL...");
                conn.Open();

                string sql = "";

                if (obj is Flavor)
                {
                    Flavor flav = (Flavor)obj;
                    sql = "INSERT INTO `Shop`.`Flavors` (`Name`, `Price`) " +
                    "VALUES ('" + flav.getName() + "', '" + flav.getPrice() + "');";
                }

                if (obj is Additive)
                {
                    Additive adtv = (Additive)obj;
                    sql = "INSERT INTO `Shop`.`Additives` (`Name`, `Price`) " +
                    "VALUES ('" + adtv.getName() + "', '" + adtv.getPrice() + "');";
                }

                if (obj is Container)
                {
                    Container ctr = (Container)obj;
                    sql = "INSERT INTO `Shop`.`Containers` (`Name`, `Price`) " +
                    "VALUES ('" + ctr.getName() + "', '" + ctr.getPrice() + "');";
                }
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                cmd.ExecuteNonQuery();
                conn.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
        }

        public static ArrayList readAll(string tableName)
        {
            ArrayList all = new ArrayList();

            try
            {
                MySqlConnection conn = new MySqlConnection(connStr);
                conn.Open();

                string sql = "SELECT * FROM `Shop`."+tableName;
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                MySqlDataReader rdr = cmd.ExecuteReader();

                while (rdr.Read())
                {
                    //Console.WriteLine(rdr[0] + " -- " + rdr[1]);
                    Object[] numb = new Object[rdr.FieldCount];
                    rdr.GetValues(numb);
                    //Array.ForEach(numb, Console.WriteLine);
                    all.Add(numb);
                }
                rdr.Close();
                conn.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }

            return all;
        }

        public static void updateContainer(int id, int container)
        {
                try
                {
                MySqlConnection conn = new MySqlConnection(connStr);
                conn.Open();
                MySqlCommand cmd = new MySqlCommand();
                cmd.CommandText = "UPDATE Shop.Orders SET ContainerType = @container WHERE idOrder = @id";
                cmd.Parameters.AddWithValue("@container",container);
                cmd.Parameters.AddWithValue("@id",id);
                cmd.Connection = conn;
                cmd.ExecuteNonQuery();
                conn.Close();
                }
                catch (Exception ex)
                {
                    Console.WriteLine(ex.ToString());
                }        
        }

        public static ArrayList getScoops(int id)
        {
            ArrayList all = new ArrayList();

            try
            {
                MySqlConnection conn = new MySqlConnection(connStr);
                conn.Open();

                string sql = "SELECT * FROM `Shop`.`OrderScoops` WHERE idOrder ='"+id+"';";
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                MySqlDataReader rdr = cmd.ExecuteReader();

                while (rdr.Read())
                {
                    Object[] numb = new Object[rdr.FieldCount];
                    rdr.GetValues(numb);
                    int scoopId = Convert.ToInt16(numb[1]);

                    MySqlConnection conn_flavs = new MySqlConnection(connStr);
                    conn_flavs.Open();
                    
                    sql = "SELECT * FROM `Shop`.`Flavors` WHERE idFlavor ='"+scoopId+"';";
                    cmd = new MySqlCommand(sql, conn_flavs);
                    MySqlDataReader rdr_flavors = cmd.ExecuteReader();
                    while (rdr_flavors.Read())
                    {
                        Object[] flav = new Object[rdr_flavors.FieldCount];
                        rdr_flavors.GetValues(flav);
                        all.Add(flav[1]);
                    }
                    rdr_flavors.Close();
                    conn_flavs.Close();
                }
                rdr.Close();
                conn.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }

            return all;        
            }
        public static string getAdditiveName(int id)
        {
            try
            {
                MySqlConnection conn = new MySqlConnection(connStr);
                conn.Open();

                string sql = "SELECT * FROM `Shop`.`Additives` WHERE idAdditive ='"+id+"';";
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                MySqlDataReader rdr = cmd.ExecuteReader();

                while (rdr.Read())
                {
                    Object[] adtv = new Object[rdr.FieldCount];
                    rdr.GetValues(adtv);
                    string? name = Convert.ToString(adtv[1]);

                    if (name is null)
                    return " ";

                    return name;
                }
                rdr.Close();
                conn.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }

            return " ";        
        }

        public static string getContainerName(int id)
        {
            try
            {
                MySqlConnection conn = new MySqlConnection(connStr);
                conn.Open();

                string sql = "SELECT * FROM `Shop`.`Containers` WHERE idContainer ='"+id+"';";
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                MySqlDataReader rdr = cmd.ExecuteReader();

                while(rdr.Read())
                {
                    string? name = Convert.ToString(rdr["Name"]);
                    Console.WriteLine("~~"+name+"~~");
                    if (!(name is null))
                        return name;
                }
                conn.Close();
                conn.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }

            return " ";        
        }
        public static int addSale(int id)
        {
            int sum = 0;
            int idSale = -1;
            int idContainer = -1;
            int idAdtv = -1;
            try
            {
                MySqlConnection conn = new MySqlConnection(connStr);
                conn.Open();

                string sql = "SELECT * FROM `Shop`.`Orders` WHERE idOrder ='"+id+"';";
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                MySqlDataReader rdr = cmd.ExecuteReader();

                while(rdr.Read())
                {
                    idContainer = Convert.ToInt16(rdr["ContainerType"]);
                    idAdtv = Convert.ToInt16(rdr["Additive"]);
                }
                conn.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }

            if (idContainer == -1)
            {
                return -1;
            }

            try // ADD CONTAINER PRICE
            {
                MySqlConnection conn = new MySqlConnection(connStr);
                conn.Open();

                string sql = "SELECT * FROM `Shop`.`Containers` WHERE idContainers ='"+idContainer+"';";
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                MySqlDataReader rdr = cmd.ExecuteReader();

                while(rdr.Read())
                {
                    int p = Convert.ToInt16(rdr["Price"]);
                    sum += p;
                    Console.Write("Price for a " + rdr["Name"] + " is " + p +", ");
                }
                conn.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }

            try // ADD ADDITIVE PRICE
            {
                MySqlConnection conn = new MySqlConnection(connStr);
                conn.Open();

                string sql = "SELECT * FROM `Shop`.`Additives` WHERE idAdditive ='"+idAdtv+"';";
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                MySqlDataReader rdr = cmd.ExecuteReader();

                while(rdr.Read())
                {
                    int p = Convert.ToInt16(rdr["Price"]);
                    sum += p;
                    Console.Write("price for the " + rdr["Name"] + " is " + p +", ");
                }
                conn.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }

            try // ADD PRICE FOR EACH SCOOP
            {
                MySqlConnection conn = new MySqlConnection(connStr);
                conn.Open();

                string sql = "SELECT COUNT(*) FROM `Shop`.`OrderScoops` WHERE idOrder ='"+id+"';";
                using (var cmd = new MySqlCommand(sql, conn))
                {
                    int p = 0;
                    int count = Convert.ToInt32(cmd.ExecuteScalar());
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
                    sum += p;
                }
                conn.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }

            try // ADD SALE
            {
                MySqlConnection conn = new MySqlConnection(connStr);
                conn.Open();

                DateTime date = DateTime.Now;
                string d = date.ToString("yyyy-MM-dd H:mm:ss");

                string sql = "INSERT INTO `Shop`.`Sales` (`Date`, `Sum`, `idOrder`) " +
                "VALUES ('" + d + "', '"  + sum + "', '"  + id + "');";

                MySqlCommand cmd = new MySqlCommand(sql, conn);
                cmd.ExecuteNonQuery();
                idSale = (int)cmd.LastInsertedId;

                conn.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }

            Console.WriteLine("Total of " + sum +".");
            return idSale;
        }

    public static string getScoopName(int id)
        {
            try
            {
                MySqlConnection conn = new MySqlConnection(connStr);
                conn.Open();

                string sql = "SELECT * FROM `Shop`.`Flavors` WHERE idFlavor ='"+id+"';";
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                MySqlDataReader rdr = cmd.ExecuteReader();

                while(rdr.Read())
                {
                    string? name = Convert.ToString(rdr["Name"]);
                    if (!(name is null))
                        return name;
                }
                conn.Close();
                conn.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }

            return " ";        
        }
}
}