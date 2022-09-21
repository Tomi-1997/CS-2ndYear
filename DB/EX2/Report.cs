using MySql.Data.MySqlClient;
class myReport
{
    int salesSum;
    int salesNum;
    double salesAvg;

    public myReport()
    {
        this.salesAvg = 0; this.salesNum = 0; this.salesSum = 0;
    }

    void updateSales()
    {
        try
            {
                MySqlConnection conn = new MySqlConnection(MySqlAccess.MySqlAccess.connStr);
                conn.Open();

                string sql = "SELECT * FROM `Shop`.`Sales`;";
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                MySqlDataReader rdr = cmd.ExecuteReader();

                while (rdr.Read())
                {
                    Object[] sales = new Object[rdr.FieldCount];
                    rdr.GetValues(sales);

                    this.salesSum += Convert.ToInt16(rdr["Sum"]);
                    this.salesNum++;
                }

                this.salesAvg = (this.salesSum) / (this.salesNum);
                rdr.Close();
                conn.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
    }

    public void sales()
    {
        updateSales();
        Console.WriteLine("Total sales: " + this.salesNum);
        Console.WriteLine("Total sum: " + this.salesSum + " NIS");
        Console.WriteLine("Average of: " + this.salesAvg + " NIS");
    }

    public void unCompletedSales()
    {
        try
            {
                MySqlConnection conn = new MySqlConnection(MySqlAccess.MySqlAccess.connStr);
                conn.Open();

                string sql = "SELECT * FROM `Shop`.`Orders` WHERE idOrder NOT IN (SELECT idOrder FROM `Shop`.`Sales`);";
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                MySqlDataReader rdr = cmd.ExecuteReader();

                while (rdr.Read())
                {
                    Object[] sales = new Object[rdr.FieldCount];
                    rdr.GetValues(sales);
                    System.Console.WriteLine("Order number " + rdr["idOrder"] + " has not been completed.");
                }
                rdr.Close();
                conn.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
    }

    public void favScoop()
    {
        try
            {
                MySqlConnection conn = new MySqlConnection(MySqlAccess.MySqlAccess.connStr);
                conn.Open();

                string completedSales = "SELECT * FROM `Shop`.`OrderScoops` WHERE idOrder IN (SELECT idOrder FROM `Shop`.`Sales`)";
                string sql = "SELECT idScoop, COUNT(*) as fav FROM (" + completedSales + ") as SOLD";
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                MySqlDataReader rdr = cmd.ExecuteReader();

                while (rdr.Read())
                {
                    Object[] sales = new Object[rdr.FieldCount];
                    rdr.GetValues(sales);
                    System.Console.WriteLine("Favourite scoop is " + MySqlAccess.MySqlAccess.getScoopName(Convert.ToInt16(rdr["idScoop"])) +  " with " + rdr["fav"] + " scoops.");
                }
                rdr.Close();
                conn.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
    }

    public void favAdtv()
    {
        try
            {
                MySqlConnection conn = new MySqlConnection(MySqlAccess.MySqlAccess.connStr);
                conn.Open();

                string completedSales = "SELECT * FROM `Shop`.`Orders` WHERE idOrder IN (SELECT idOrder FROM `Shop`.`Sales`)";
                string sql = "SELECT Additive, COUNT(*) as fav FROM (" + completedSales + ") as SOLD WHERE Additive > 0;";
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                MySqlDataReader rdr = cmd.ExecuteReader();

                while (rdr.Read())
                {
                    Object[] orders = new Object[rdr.FieldCount];
                    rdr.GetValues(orders);
                    System.Console.WriteLine("Favourite additive is " + MySqlAccess.MySqlAccess.getAdditiveName(Convert.ToInt16(rdr["Additive"])) +  " with " + rdr["fav"] + " orders.");
                }
                rdr.Close();
                conn.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
    }

    public void favoriteItems()
    {
        favScoop();
        favAdtv();
    }
}
