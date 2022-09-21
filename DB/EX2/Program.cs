using BusinessLogic;

Logic.updateInfo(); // Updates info dictionary, displays to the customer which scoop blocks which additive.
Console.WriteLine("Hi, type 0 to use mySQL, type 1 to use Mongo.");
int userInput = OrderHelper.myOrder.getInput();
if (userInput == 1)
{
    MODE.CURR = MODE.MONGO;
}
else
{
    MODE.CURR = MODE.SQL;
}
Console.WriteLine("Hello, would you like to:");
while (userInput != -1)
{
    Console.WriteLine("1 - Start new order.");
    Console.WriteLine("2 - Print flavors info.");
    Console.WriteLine("3 - Print additives info.");
    Console.WriteLine("4 - Print containers info.");
    Console.WriteLine("5 - Queries");
    Console.WriteLine("(-1) - for exit");

    userInput = OrderHelper.myOrder.getInput();
    Console.Clear();

    switch (userInput)
    {
        case 0:
            // BusinessLogic.Logic.createTables();
            break;
        case 1:
            if (OrderHelper.myOrder.startOrder() == -1)
            {
                Console.WriteLine("Order failed");
            }
            break;
        case 2:
            BusinessLogic.Logic.printTableData("Flavors");
            break;
        case 3:
            BusinessLogic.Logic.printTableData("Additives");
            break;
        case 4:
            BusinessLogic.Logic.printTableData("Containers");
            break;
        case 5:
            OrderHelper.myOrder.displayQueries();
            break;
    }

}

Console.WriteLine("Program ended.");

public static class MODE
{
    public const int SQL = 0;
    public const int MONGO = 1;
    public static int CURR = -1;
}