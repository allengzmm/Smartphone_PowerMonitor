
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A demonstration application showing a time series chart where you can dynamically add
 * (random) data by clicking on a button.
 *
 */
public class DynamicDataDemo{
	
	private static final long serialVersionUID = 1L;
	ValueAxis Vaxis;
    private TimeSeries series;

    private double lastValue = 0.0;
    public DynamicDataDemo(final String title) {
        this.series = new TimeSeries("Power[mW]", Millisecond.class);
        final TimeSeriesCollection dataset = new TimeSeriesCollection(this.series);
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        GUI.panel3.add(chartPanel);
        chartPanel.setPreferredSize(new java.awt.Dimension(474, 245));

    }
	private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
            "Dynamic Power Consumption", 
            "Time", 
            "Value",
            dataset, 
            true, 
            true, 
            false
        );
        final XYPlot plot = result.getXYPlot();
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(700000.0);  
        axis = plot.getRangeAxis();
        Vaxis=axis;
        plot.getRenderer().setSeriesStroke(0,new BasicStroke(3.0f));
        return result;
    }
    
    public void refreshData(final double power){
      
      this.lastValue = power;
      final Millisecond now = new Millisecond();
      System.out.println("Now = " + now.toString());
      this.series.add(new Millisecond(), this.lastValue);
      if(GUI.get_resaxis()!=0){Vaxis.setRange(0.0,GUI.get_resaxis());}
    }
}