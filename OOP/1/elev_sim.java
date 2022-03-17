import com.google.gson.*;
import com.google.gson.Gson;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class elev_sim {
    static boolean DEBUG = false;
    /**
     * INPUT: json file of building, csv file of allocated calls
     * OUTPUT: drawing building and movement of each elevator to calls
     **/
    public static void main(String[] args) throws Exception
    {
        // Default names
        String address = "C:\\Users\\tomto\\IdeaProjects\\RandomMethods\\src";
        String building_name = address.concat("\\B4.json");
        String calls_name = address.concat("\\B4_d.csv");
        // If valid , get input
        if (args.length == 2) {
            try {
                building_name = args[0];
                calls_name = args[1];
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // INIT BUILDING
        Building b = (Building) get_building_from_json(building_name, Building.class);
        // Calls

        // Init StdDraw
        double x = b._maxFloor - 20;
        double width = 10;
        double height = b._maxFloor - b._minFloor;

        StdDraw.setCanvasSize(700, 700);

        double std_height = b._maxFloor * 1.2;
        if (std_height > 70)
            std_height = 70;
        StdDraw.setXscale(b._minFloor, b._maxFloor * 1);
        StdDraw.setYscale(b._minFloor, std_height);
        StdDraw.clear(Color.black);


        // DRAW ELEVATORS
        Color[] colors_elev = new Color[b._elevators.length];
        setColors(colors_elev);


        // FROM CSV FILE ----> ArrayList that each element is a string with data of call
        Scanner sc = new Scanner(new File(calls_name));
        ArrayList<String> data_csv = new ArrayList<>();
        sc.useDelimiter(",");   //sets the delimiter pattern
        while (sc.hasNext())
        {
            data_csv.add(sc.nextLine());
        }
        sc.close();  //closes the scanner

        String[] info = data_csv.get(data_csv.size()-1).split(",");
        double finish_time = Double.parseDouble(info[1])  + Double.parseDouble(info[8]);

        // Init time array for each elevator
        for (Elevator e:b._elevators)
        {
            e.position_at_index_time = new double[(int) finish_time];
        }



        // FROM CSV FILE GET EACH ELEVATOR'S POSITION
        for (String s:data_csv)
        {
            // check if call is done. (check if ,done is in string)
            if (s.indexOf(",Done") - 1 > 0)
            {
                try
                {
                    String[] current_info = s.split(",");
                    // Current_info example : Elevator call,4.37472729,0,-1,3,1,Done,dt,5.62527271
                    int current_elev = Integer.parseInt(current_info[5]);
                    int elev_pos_at_time = Integer.parseInt(current_info[3]);
                    double current_call_got = Double.parseDouble(current_info[1]);
                    double current_call_finish = Double.parseDouble(current_info[8]);
                    int elev_pos = (int)(current_call_finish + current_call_got);

                    if (current_call_finish + current_call_got < finish_time)
                    {
                        b._elevators[current_elev].set_pos_arr(elev_pos_at_time, (elev_pos));

                        if (DEBUG)
                            System.out.println("Elev:"+current_elev+", Pos:" + elev_pos_at_time + ", Time:" + elev_pos );

                    }
                }
                catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
            }
        }

        // FILL GAPS IN EACH ELEVATOR'S POSITION ARRAY
        for (Elevator e: b._elevators)
        {
            fill_gaps(e.position_at_index_time);
        }

        for (Double d: b._elevators[1].position_at_index_time)
        {
            System.out.println(d + " ");
        }

        // DRAW BUILDING AND ELEVATORS UNTIL TIME IS UP
        int fin =  b._elevators[0].position_at_index_time.length;

        for (int i = 0; i <fin; i = i + 40)
        {
            draw_building(x, width, b);
            draw_elevs(x,width,b, colors_elev, i );

            if (i == 0)
                refresh(2000);
            else
                refresh(1000);
        }

        // End of main function
    }

        private static void reduce_gaps(double[] pos)
    {
        int first_index = 0;
        double first = pos[first_index];
        int second_index = getNext_index(pos, 0);
        double second;
        while (second_index != -1)
        {
            second = pos[second_index];
            int sign = 1;
            if (first > second) sign = -1;
            double dist_avg = Math.abs(second - first) / (second_index - first_index);
            for (int i = first_index + 1; i < second_index; i++)
            {
                pos[i] = pos[i-1] + (dist_avg * sign);
            }

            first_index = second_index;
            first = pos[first_index];

            second_index = getNext_index(pos, first_index);
        }
    }

    private static int getNext_index(double[] a, int i)
    {
        double current = a[i];
        i++;
        while (i < a.length && a[i] == current)
        {
            i++;
        }
        if (i == a.length || a[i] == current) return -1;
        return i;
    }

    private static void draw_elevs(double x, double width, Building b, Color[] colors_elev, int time) {
        double elev_rad = 0.1;
        for (int i = 0; i < b._elevators.length; i++)
        {
            StdDraw.setPenColor(colors_elev[i]);
            StdDraw.filledSquare(x - (i + 2) * width * 0.9, b._elevators[i].position_at_index_time[time], width * 0.25);
        }
    }

    private static void draw_building(double x, double width, Building b)
    {
        StdDraw.setPenColor(Color.lightGray);
        StdDraw.line(x - width, b._minFloor, x - width, b._maxFloor);
        StdDraw.line(x + width, b._minFloor, x + width, b._maxFloor);
        for (int i = b._minFloor; i <= b._maxFloor; i++) {
            StdDraw.line(x - width, i, x + width, i);
            StdDraw.text(x + width * 1.2, i, "" + i);
        }
        // DRAW ROOF
        StdDraw.line(x - width, b._maxFloor, x, b._maxFloor + 2);
        StdDraw.line(x + width, b._maxFloor, x, b._maxFloor + 2);
    }

    private static void setColors(Color[] colors_elev)
    {
        for (int i = 0; i < colors_elev.length; i++)
        {
            if (i % 3 == 0)
            {
                colors_elev[i] = Color.red;
            }
            else if (i % 3 == 1)
            {
                colors_elev[i] = Color.orange;

            }
            else
            {
                colors_elev[i] = Color.green;

            }
        }
    }
    private static void refresh(long time)
    {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        StdDraw.clear(Color.black);
    }
    private static void fill_gaps(double[] position_at_index_time)
    {
        int first_non_zero = 0;
        int second_none_zero = 0;
        int counter_zeroes = 0;
        for (int i = 5; i < position_at_index_time.length; i++)
        {
            counter_zeroes = 0;
            while (i < position_at_index_time.length && position_at_index_time[i] == 0)
            {
                counter_zeroes++;
                i++;
            }
            if (i>= position_at_index_time.length)
                break;

            second_none_zero = i;
            double dist = Math.abs(position_at_index_time[first_non_zero] - position_at_index_time[second_none_zero]);
            int z = i - counter_zeroes;
            if (position_at_index_time[second_none_zero] != position_at_index_time[i])
            {
                for (;z<i;z++)
                {
                    position_at_index_time[z] = position_at_index_time[second_none_zero] * dist / counter_zeroes * (z+1);
                }
            }
            else
            {
                for (;z<i;z++)
                    position_at_index_time[z] = position_at_index_time[i];
            }
        }
        reduce_gaps(position_at_index_time);
    }



        public static Object get_building_from_json (String filename, Class classObject)
        {
            String input = "";
            String s;
            try {
                FileReader f = new FileReader(filename);
                BufferedReader readFile = new BufferedReader(f);
                s = readFile.readLine();
                while (s != null) {
                    input = input + s;
                    s = readFile.readLine();
                }
                f.close();
                readFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            Object b = gson.fromJson(input, classObject);
            return b;
        }

        class Building
        {
            int _minFloor, _maxFloor;
            Elevator[] _elevators;

            public Building(int min_f, int max_f, Elevator[] elev_arr) {
                this._minFloor = min_f;
                this._maxFloor = max_f;
                this._elevators = elev_arr;
            }
        }

        class Elevator
        {
            double _speed;
            double[] position_at_index_time;
            public void set_pos_arr(int pos, int time)
            {
                    this.position_at_index_time[time] = pos;
            }
        }

    }
