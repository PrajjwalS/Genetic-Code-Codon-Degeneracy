import java.awt.Color;
import java.awt.BasicStroke;

import java.util.Vector;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
public class XY_Plot extends ApplicationFrame {
    public XY_Plot(String applicationTitle, String chartTitle, Vector data) {
        super(applicationTitle);
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "Gene Sequence per 100 sample",
                "SKEW GvsC values",
                createDataset(data),
                PlotOrientation.VERTICAL,
                true, true, false);
        ChartPanel chartPanel = new ChartPanel(xylineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        final org.jfree.chart.plot.XYPlot plot = xylineChart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesPaint( 0 , Color.RED );
        renderer.setSeriesPaint( 1 , Color.GREEN );
        renderer.setSeriesPaint( 2 , Color.BLUE);

        renderer.setSeriesStroke( 0 , new BasicStroke( 0.1f ) );
        renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
        renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );

        plot.setRenderer( renderer );
        setContentPane( chartPanel );
    }

    private XYDataset createDataset(Vector data)
    {
        final XYSeries GC_SKEW = new XYSeries("GvsC skew curve");
        double min=(double)data.get(0);long mini=0;
        double max=(double)data.get(0);long maxi=0;
        for(int i=0;i<data.size();i++)
        {
                GC_SKEW.add(((double) i + 1) * 100, (Double) data.get(i));
                //getting min and max point
                if((Double) data.get(i)>max)
                {
                    max = (Double) data.get(i);
                    maxi=i;
                }
                if((Double) data.get(i)<min)
                {
                    min=(Double) data.get(i);
                    mini=i;
                }
        }

        final XYSeries MIN = new XYSeries( "Minimum X Point" );
        MIN.add( mini , min );
        MIN.add( mini+1 , min+1 );
        MIN.add( mini-1 , min+1 );

        final XYSeries MAX = new XYSeries( "Maximum Y Point" );
        MAX.add( maxi , max );
        MAX.add( maxi+1 , max-1 );
        MAX.add( maxi-1 , max-1 );


        final XYSeriesCollection dataset = new XYSeriesCollection();

        dataset.addSeries(GC_SKEW);
        dataset.addSeries(MIN);
        dataset.addSeries(MAX);

        return dataset;
    }
}
