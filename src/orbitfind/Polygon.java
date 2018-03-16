/**
 * Created by Galux on 8/7/2016.
 */
package orbitfind;

import java.util.ArrayList;

public class Polygon
{
    private ArrayList<Double> lat_array ;
    private ArrayList<Double> long_array;


    public Polygon(ArrayList<Double> lat_array, ArrayList<Double> long_array)
    {
        this.lat_array = lat_array;
        this.long_array = long_array;
    }

    public Double findMinLat()
    {
        Double min = lat_array.get(0);

        for(int i=1; i<lat_array.size(); i++)
        {
            if(min>lat_array.get(i))
            {
                min = lat_array.get(i);
            }
        }

        return min;
    }

    public Double findMinLong()
    {
        Double min = long_array.get(0);

        for(int i=1; i<long_array.size(); i++)
        {
            if(min>long_array.get(i))
            {
                min = long_array.get(i);
            }
        }

        return min;
    }

    public Double findMaxLat()
    {
        Double max = lat_array.get(0);

        for(int i=1; i<lat_array.size(); i++)
        {
            if(max<lat_array.get(i))
            {
                max = lat_array.get(i);
            }
        }

        return max;
    }

    public Double findMaxLong()
    {
        Double max = long_array.get(0);

        for(int i=1; i<long_array.size(); i++)
        {
            if(max<long_array.get(i))
            {
                max = long_array.get(i);
            }
        }

        return max;
    }

    public ArrayList<Double> getLat()
    {
        return this.lat_array;
    }

    public ArrayList<Double> getLong()
    {
        return this.long_array;
    }

    

}