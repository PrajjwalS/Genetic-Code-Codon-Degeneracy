import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import java.util.Map;

public class bar_chart_creator extends ApplicationFrame
{

    public bar_chart_creator(String applicationTitle , String chartTitle, Map<Character, Long> freq)
    {
        super( applicationTitle );
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "AA one letter code",
                "Occurance percentage",
                createDataset(freq),
                PlotOrientation.VERTICAL,
                true, true, false);

        Color colorOverride = Color.BLACK;
        Stroke solid = new BasicStroke(1);
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setRangeGridlineStroke(solid);
        ChartPanel chartPanel = new ChartPanel( barChart );
        chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }
    private CategoryDataset createDataset( Map<Character, Long> freq )
    {

        // generating total count
        long count=0;
        for (Map.Entry<Character, Long> entry : freq.entrySet())
            count+=entry.getValue();
        System.out.println("Count:"+count);
        final DefaultCategoryDataset dataset =
                new DefaultCategoryDataset( );

        // making dataset for histogram
        for (Map.Entry<Character, Long> entry : freq.entrySet())
            dataset.addValue(((double)entry.getValue()/(double)count)*100,entry.getKey(),entry.getKey());



        return dataset;
    }

}
