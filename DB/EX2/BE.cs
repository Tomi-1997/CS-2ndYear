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
            return base.ToString() + ": " + name + " , " + info +", extra cost:" + price;
        }
    }
}
