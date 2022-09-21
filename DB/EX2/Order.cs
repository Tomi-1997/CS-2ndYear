using BusinessLogic;
using System.Collections;  
namespace OrderHelper
{
    class myOrder
    {
        public static int getInput()
        {
            string? s = Console.ReadLine();
            if (s is null || s == "")
                {
                    Console.WriteLine("Failed to receive input, terminating.");
                    return -1;
                }
            return Int32.Parse(s);
        }


        public static int startOrder()
        {
            int id = -1;
            if ( MODE.CURR == MODE.SQL)
            {id = MySqlAccess.MySqlAccess.addOrder();}
            if (MODE.CURR == MODE.MONGO)
            {id = MyMongoAccess.MyMongoAccess.addOrder();}

            if (id == -1)
            {
                System.Console.WriteLine("Error attemptinting to start order.");
                return -1;
            }
            return selectContainer(id);
        }

        static int selectContainer(int id)
        {
            Console.Clear();
            Console.WriteLine("Select serve type by ID.");
            BusinessLogic.Logic.printTableData("Containers");

            int containerId = getInput();

            if ( MODE.CURR == MODE.SQL)
            {MySqlAccess.MySqlAccess.updateContainer(id, containerId);}

            if (MODE.CURR == MODE.MONGO)
            {MyMongoAccess.MyMongoAccess.updateContainer(id, containerId);}
            return selectScoops(id, containerId - 1);
        }

        static int selectScoops(int id, int container)
        {
            Console.Clear();
            int afterScoop = Logic.flavNum() + 1;
            Console.WriteLine("Select scoops by ID, type 0 to finish order, type " + afterScoop + " to finish scoop selection and add additives.");
            BusinessLogic.Logic.printTableData("Flavors");

            int scoops = 0;
            int ui = getInput();
            while (ui != -1)
            {
                if (ui == afterScoop) // Add additive
                {
                    if (scoops > 0)
                    {
                        if (Logic.CONTAINERS[container] == "Cone" && scoops < 2)
                        {
                            Console.WriteLine("Regular cone has to have atleast 2 scoops for an additive.");
                        }
                        else
                        {
                            return selectAdditive(id);
                        }
                    }
                    else
                        Console.WriteLine("No scoops added yet, cannot advance.");        
                }
                else if (ui == 0) // Finish order
                {
                    if (scoops > 0)
                        return finishOrder(id);
                    else
                        Console.WriteLine("No scoops added yet, cannot advance.");
                }
                else if (ui > afterScoop) // Invalid
                {
                    Console.WriteLine("Invalid command.");
                }
                else
                {
                    if (scoops < Logic.CNTR_LIM[container])
                    {

                        if (MODE.CURR == MODE.SQL)
                            MySqlAccess.MySqlAccess.addOrderScoop(id, ui);
                        if (MODE.CURR == MODE.MONGO)
                            MyMongoAccess.MyMongoAccess.addOrderScoop(id, ui);
                        Console.WriteLine("Scoop added");
                        scoops++;
                    }
                    else
                    {
                        Console.WriteLine("Scoop limit reached, cannot add more.");
                    }
                }
                ui = getInput();
            }
            return 0;
        }

        static int selectAdditive(int id)
        {
            Console.Clear();
            Console.WriteLine("Select additives by ID, type 0 to finish order.");
            BusinessLogic.Logic.printTableData("Additives");
            int ui = 1;
            ui = getInput();
            while (ui != 0)
            {
                if (ui > Logic.adtvNum() || ui < 0)
                {
                    Console.WriteLine("Invalid id inserted");
                    ui = getInput();
                    continue;
                }
                Boolean compatible = true;
                ArrayList scoops = new ArrayList();
                string additive = "";

                if (MODE.CURR == MODE.SQL)
                {
                    scoops = MySqlAccess.MySqlAccess.getScoops(id);
                    additive = MySqlAccess.MySqlAccess.getAdditiveName(ui);
                }

                if (MODE.CURR == MODE.MONGO)
                {
                    scoops = MyMongoAccess.MyMongoAccess.getScoops(id);
                    additive = MyMongoAccess.MyMongoAccess.getAdditiveName(ui); 
                }
                    
                foreach (string scoop in scoops)
                {
                    if (Logic.isBlocking(scoop, additive))
                        {
                            compatible = false;
                            Console.WriteLine(additive +" not compatible with " + scoop + ", please select another or finish the order.");
                            break;
                        }
                }
                if (compatible)
                {
                    Console.WriteLine("Updated, type 0 to finish or choose another additive");

                    if (MODE.CURR == MODE.SQL)
                        MySqlAccess.MySqlAccess.updateOrderAdditive(id, ui);

                    if (MODE.CURR == MODE.MONGO)
                        MyMongoAccess.MyMongoAccess.updateOrderAdditive(id, ui);
                }
                ui = getInput();
            }
            return finishOrder(id);
        }

        static int finishOrder(int id)
        {
            Random rnd = new Random();
            int saleId = -1;

            if (MODE.CURR == MODE.SQL)
                saleId = MySqlAccess.MySqlAccess.addSale(id);
            if (MODE.CURR == MODE.MONGO)
                saleId = MyMongoAccess.MyMongoAccess.addSale(id);

            Console.WriteLine("Sale added, your sale number is " + saleId + ", please collect your order at register "  + rnd.Next(1,5) +".");
            return 0;
        }

        public static void displayQueries()
        {
            Console.Clear();
            Console.WriteLine("Hello, would you like to:\n1- See sales sum, count and average.\n2- Uncompleted sales. \n3- See most popular scoop flavor and additive.");
            int ui = getInput();
            Console.Clear();
            myReport rep = new myReport();

            switch (ui)
            {
                case 1:
                    rep.sales();
                    break;
                case 2:
                    rep.unCompletedSales();
                    break;
                case 3:
                    rep.favoriteItems();
                    break;
            }
        }

    }
}