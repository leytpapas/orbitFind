/**
 * Created by Sarantis on 1/21/2016.
 */

package orbitfind;

import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

public class KML_Creator
{
    private StringBuilder str_bld,copy;
    private int tabs=0; //kolpo!
    public KML_Creator()
    {
        str_bld = new StringBuilder();
        str_bld.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        str_bld.append("<kml xmlns=\"http://earth.google.com/kml/2.0\">\n");
        openField("","Document");
        str_bld.append("<Style id=\"polygon_style\">\n" +
"		<LineStyle>\n" +
"			<color>ff000000</color>\n" +
"		</LineStyle>\n" +
"		<PolyStyle>\n" +
"			<color>7faaaaaa</color>\n" +
"		</PolyStyle>\n" +
"	</Style>\n" +
"<Style id=\"line_label_1\">\n" +
"	<LabelStyle>\n" +
"		<scale>1.3</scale>\n" +
"	</LabelStyle>\n" +
"	<LineStyle>\n" +
"		<color>FFFFFFFF</color>\n" +
"		<width>2</width>\n" +
"		<gx:labelVisibility>1</gx:labelVisibility>\n" +
"	</LineStyle>\n" +
"</Style>\n" +
"\n" +
"<Style id=\"line_label_2\">\n" +
"	<LabelStyle>\n" +
"		<scale>1.3</scale>\n" +
"	</LabelStyle>\n" +
"	<LineStyle>\n" +
"		<color>FFFFFF00</color>\n" +
"		<width>1</width>\n" +
"		<gx:labelVisibility>1</gx:labelVisibility>\n" +
"	</LineStyle>\n" +
"</Style>\n" +
"<Style id=\"line_label_3\">\n" +
"	<LabelStyle>\n" +
"		<scale>1.3</scale>\n" +
"	</LabelStyle>\n" +
"	<LineStyle>\n" +
"		<color>FFFF00FF</color>\n" +
"		<width>1</width>\n" +
"		<gx:labelVisibility>1</gx:labelVisibility>\n" +
"	</LineStyle>\n" +
"</Style>\n" +
"<Style id=\"line_label_4\">\n" +
"	<LabelStyle>\n" +
"		<scale>1.3</scale>\n" +
"	</LabelStyle>\n" +
"	<LineStyle>\n" +
"		<color>FFFF0000</color>\n" +
"		<width>1</width>\n" +
"		<gx:labelVisibility>1</gx:labelVisibility>\n" +
"	</LineStyle>\n" +
"</Style>\n" +
"<Style id=\"line_label_5\">\n" +
"	<LabelStyle>\n" +
"		<scale>1.3</scale>\n" +
"	</LabelStyle>\n" +
"	<LineStyle>\n" +
"		<color>FFC0C0C0</color>\n" +
"		<width>1</width>\n" +
"		<gx:labelVisibility>1</gx:labelVisibility>\n" +
"	</LineStyle>\n" +
"</Style>\n" +
"<Style id=\"line_label_6\">\n" +
"	<LabelStyle>\n" +
"		<scale>1.3</scale>\n" +
"	</LabelStyle>\n" +
"	<LineStyle>\n" +
"		<color>FF808080</color>\n" +
"		<width>1</width>\n" +
"		<gx:labelVisibility>1</gx:labelVisibility>\n" +
"	</LineStyle>\n" +
"</Style>\n" +
"\n" +
"<Style id=\"line_label_7\">\n" +
"	<LabelStyle>\n" +
"		<scale>1.3</scale>\n" +
"	</LabelStyle>\n" +
"	<LineStyle>\n" +
"		<color>FF808000</color>\n" +
"		<width>1</width>\n" +
"		<gx:labelVisibility>1</gx:labelVisibility>\n" +
"	</LineStyle>\n" +
"</Style>\n" +
"\n" +
"<Style id=\"line_label8\">\n" +
"	<LabelStyle>\n" +
"		<scale>1.3</scale>\n" +
"	</LabelStyle>\n" +
"	<LineStyle>\n" +
"		<color>FF800080</color>\n" +
"		<width>1</width>\n" +
"		<gx:labelVisibility>1</gx:labelVisibility>\n" +
"	</LineStyle>\n" +
"</Style>\n" +
"\n" +
"<Style id=\"line_label_9\">\n" +
"	<LabelStyle>\n" +
"		<scale>1.3</scale>\n" +
"	</LabelStyle>\n" +
"	<LineStyle>\n" +
"		<color>FF800000</color>\n" +
"		<width>1</width>\n" +
"		<gx:labelVisibility>1</gx:labelVisibility>\n" +
"	</LineStyle>\n" +
"</Style>\n" +
"\n" +
"<Style id=\"line_label_10\">\n" +
"	<LabelStyle>\n" +
"		<scale>1.3</scale>\n" +
"	</LabelStyle>\n" +
"	<LineStyle>\n" +
"		<color>FF00FFFF</color>\n" +
"		<width>1</width>\n" +
"		<gx:labelVisibility>1</gx:labelVisibility>\n" +
"	</LineStyle>\n" +
"</Style>\n" +
"<Style id=\"line_label_11\">\n" +
"	<LabelStyle>\n" +
"		<scale>1.3</scale>\n" +
"	</LabelStyle>\n" +
"	<LineStyle>\n" +
"		<color>FF00FF00</color>\n" +
"		<width>1</width>\n" +
"		<gx:labelVisibility>1</gx:labelVisibility>\n" +
"	</LineStyle>\n" +
"</Style>\n" +
"<Style id=\"line_label_12\">\n" +
"	<LabelStyle>\n" +
"		<scale>1.3</scale>\n" +
"	</LabelStyle>\n" +
"	<LineStyle>\n" +
"		<color>FF008080</color>\n" +
"		<width>1</width>\n" +
"		<gx:labelVisibility>1</gx:labelVisibility>\n" +
"	</LineStyle>\n" +
"</Style>\n" +
"<Style id=\"line_label_13\">\n" +
"	<LabelStyle>\n" +
"		<scale>1.3</scale>\n" +
"	</LabelStyle>\n" +
"	<LineStyle>\n" +
"		<color>FF008000</color>\n" +
"		<width>1</width>\n" +
"		<gx:labelVisibility>1</gx:labelVisibility>\n" +
"	</LineStyle>\n" +
"</Style>\n" +
"<Style id=\"line_label_14\">\n" +
"	<LabelStyle>\n" +
"		<scale>1.3</scale>\n" +
"	</LabelStyle>\n" +
"	<LineStyle>\n" +
"		<color>FF0000FF</color>\n" +
"		<width>1</width>\n" +
"		<gx:labelVisibility>1</gx:labelVisibility>\n" +
"	</LineStyle>\n" +
"</Style>\n" +
"<Style id=\"line_label_15\">\n" +
"	<LabelStyle>\n" +
"		<scale>1.3</scale>\n" +
"	</LabelStyle>\n" +
"	<LineStyle>\n" +
"		<color>FF000080</color>\n" +
"		<width>1</width>\n" +
"		<gx:labelVisibility>1</gx:labelVisibility>\n" +
"	</LineStyle>\n" +
"</Style>\n" +
"<Style id=\"line_label_16\">\n" +
"	<LabelStyle>\n" +
"		<scale>1.3</scale>\n" +
"	</LabelStyle>\n" +
"	<LineStyle>\n" +
"		<color>FF000000</color>\n" +
"		<width>1</width>\n" +
"		<gx:labelVisibility>1</gx:labelVisibility>\n" +
"	</LineStyle>\n" +
"</Style>");
    }

    public void closeCreator()
    {
//        closeField("Document");
//        closeField("kml");
        copy = new StringBuilder();
        copy.append(str_bld);
        copy.append("</Document>\n");
        copy.append("</kml>\n");
    }

    public void openField(String tab,String field)
    {
        str_bld.append(tab+"<" + field + ">\n");
    }

    public void closeField(String tab,String field)
    {
        str_bld.append(tab+ "</" + field + ">\n");
    }

    public void addCoordinates(String coordinate)
    {
        str_bld.append("\t<coordinates>" + coordinate + "</coordinates>\n");
    }

    public void addName(String name)
    {
        str_bld.append("\t<name>" + name + "</name>\n");
    }

    public void addStyleURL(String styleUrl)
    {
        str_bld.append("\t<styleUrl>" + styleUrl + "</styleUrl>\n");
    }

    public void newTrack(String name, String style, String coordinates)
    {
        openField("","Placemark");
        addName(name);
        addStyleURL(style);
        openField("","LineString");
        addCoordinates(coordinates);
        closeField("","LineString");
        closeField("","Placemark");
    }
    
    public void newPoly(String name, String style, String coordinates)
    {
        openField("\t","Placemark");
        addName(name);
        addStyleURL(style);
        openField("\t\t","Polygon");
        openField("\t\t\t","outerBoundaryIs");
        openField("\t\t\t\t","LinearRing");
        addCoordinates(coordinates);
        closeField("\t\t\t\t","LinearRing");
        closeField("\t\t\t","outerBoundaryIs");
        closeField("\t\t","Polygon");
        closeField("\t","Placemark");
    }

    public void writeToFile(String file)
    {
        File myFile = new File(file);
        FileOutputStream fos = null;

        try
        {
            fos = new FileOutputStream(myFile);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.append(copy);
            osw.close();
            fos.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}