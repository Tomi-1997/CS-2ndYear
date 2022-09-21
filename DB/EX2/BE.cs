using System.Collections;

namespace BusinessEntities
{
    class Flavor
    {
        string name;
        int price;

        public Flavor(string name, int price)
        {
            this.name = name;
            this.price = price;
        }

        public string getName() { return name; }
        public int getPrice() { return price; }

        public override string ToString()
        {
            return base.ToString() + ": " + name + " , "+price;
        }
    }
    class Additive
    {
        string name;
        int price;

        public Additive(string name, int price)
        {
            this.name = name;
            this.price = price;
        }

        public string getName() { return name; }
        public int getPrice() { return price; }

        public override string ToString()
        {
            return base.ToString() + ": " + name + " , "+price;
        }
    }
    class Container
    {
        string name;
        int price;
        int scoopLimit;
        int minScpAdtv; // Min scoop number for additives
        string info;

        public Container(string name, int price, int scpLimit, int mScpAdv, string info)
        {
            this.name = name;
            this.price = price;
            this.scoopLimit = scpLimit;
            this.minScpAdtv = mScpAdv;
            this.info = info;
        }

        public string getName() { return name; }
        public int getPrice() { return price; }

        public override string ToString()
        {
            // string info = "up to 3 ice cream scoops, additive only for 2 or 3 balls.";
            // if (isBox)
            // {
            //     info = "unlimited ice cream scoops.";
            // }
            // else if (isSpecialCone)
            // {
            //     info = "up to 3 ice cream scoops, no restriction on additives. ";
            // }
            return base.ToString() + ": " + name + " , " + info +", extra cost:" + price;
        }
    }
    class VOwn
    {
        int idOwner;
        int idVehicle;

        public VOwn(int idOwner, int idVehicle)
        {
            this.idOwner = idOwner;
            this.idVehicle = idVehicle;
        }

        public int getIdOwner() { return idOwner; }
        public int getIdVehicle() { return idVehicle; }
        
        public override string ToString()
        {
            return base.ToString() + ": " + idOwner + " , "+idVehicle;
        }
    }
    class Order
    {
        int idVehicle;
        int idTask;
        string orderDate;
        string completeDate;
        int completed;
        int payed;

        public Order(int idVehicle, int idTask,string orderDate, string completeDate, int completed, int payed)
        {
            this.idVehicle = idVehicle;
            this.idTask = idTask;
            this.orderDate = orderDate;
            this.completeDate = completeDate;
            this.completed = completed;
            this.payed = payed;
        }

        
        public int getIdVehicle() { return idVehicle; }
        public int getIdTask() { return idTask; }
        public string getOrderDate() { return orderDate; }
        public string getCompleteDate() { return completeDate; }
        public int getCompleted() { return completed; }
        public int getPayed() { return payed; }
        
        public override string ToString()
        {
            return base.ToString() + ": " + idVehicle + " , " + idTask + " , " + orderDate + " , " + completeDate + " , " + completed + " , " + payed;
        }
    }
}