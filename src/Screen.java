import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static jdk.nashorn.internal.objects.NativeString.trim;


/**
 * Created by Rahim on 5/17/2017.
 */
public class Screen extends JFrame {
    private final int WIDTH = 600;
    private final int HEIGHT = 1000;

    private JPanel map;
    private JPanel inputs;

    private JPanel timePanel;
    private Time time;
    private JTextField second;
    private JTextField minute;
    private JTextField hour;
    private JTextField day;
    private JComboBox month;
    private JTextField year;
    private JButton timeUpdate;
    private JButton now;

    private JPanel locationPanel;
    private Coordinates location;
    private JTextField latitude;
    private JComboBox northSouth;
    private JTextField longitude;
    private JComboBox westEast;
    private JButton locationUpdate;

    private CelestialObject[] sky;

    private JPanel clickedList;
    private ArrayList<JLabel> clickedStars;

    Screen(){
        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("SkyChart by Abdelrahim Hentabli");
        setResizable(false);

        time = new Time();
        location = new Coordinates(34.1478,118.1269);

        //FileReader f = new FileReader("TopStars.txt");
        //String[][] starList = f.getFile();

        DatabaseReader d = new DatabaseReader();
        String[][] starList = d.getDatabase();

        sky = new CelestialObject[300];
        for(int i = 0; i < 300; i++){
            sky[i] = new Star(Integer.parseInt(starList[i][0]),starList[i][1],starList[i][2],Integer.parseInt(starList[i][3]),Integer.parseInt(starList[i][4]),0,Double.parseDouble(starList[i][5]),Double.parseDouble(starList[i][6]));
            sky[i].update(location,time);
            
        }

        //this.setLayout(new BorderLayout());


        map = new JPanel();
        map.setBackground(Color.black);
        map.setLayout(new BorderLayout());
        map.setSize(new Dimension(WIDTH,WIDTH));
        this.add(map,BorderLayout.CENTER);
        map.add(new CircleFrame(600));
        this.setVisible(true);

        drawStars();
        
        map.addMouseListener(new ClickListener());

        inputs = new JPanel();
        inputs.setPreferredSize(new Dimension(WIDTH, HEIGHT - WIDTH - getInsets().top ));
        inputs.setLayout(new FlowLayout());

        timePanel = new JPanel();

        timeUpdate = new JButton("Update");
        timeUpdate.addActionListener(new TimeListener());
        now = new JButton("Now");
        now.addActionListener(new NowListener());

        second = new JTextField(String.format("%02d", time.getSecond()),2);
        Font temp = second.getFont().deriveFont(Font.PLAIN, 16f);
        second.setFont(temp);
        minute = new JTextField(String.format("%02d",time.getMinute()),2);
        minute.setFont(temp);
        hour = new JTextField(String.format("%02d",time.getHour()),2);
        hour.setFont(temp);
        day = new JTextField(String.format("%02d",time.getDay()),2);
        day.setFont(temp);
        String[] Months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        month = new JComboBox(Months);
        month.setSelectedIndex(time.getMonth());
        year = new JTextField(String.format("%04d",time.getYear()),4);
        year.setFont(temp);


        locationPanel = new JPanel();

        locationUpdate = new JButton("Update");
        locationUpdate.addActionListener(new LocationListener());


        westEast = new JComboBox(new String[]{"W", "E"});
        northSouth = new JComboBox(new String[]{"N", "S"});

        latitude = new JTextField(String.format("%8.4f",location.getLatitude()),8);
        latitude.setFont(temp);
        longitude = new JTextField(String.format("%8.4f",location.getLongitude()),8);
        longitude.setFont(temp);

        if(location.getWest()){
            westEast.setSelectedIndex(0);
        }
        else{
            westEast.setSelectedIndex(1);
        }
        if(location.getNorth()){
            northSouth.setSelectedIndex(0);
        }
        else{
            northSouth.setSelectedIndex(1);
        }


        timePanel.add(year);
        timePanel.add(new JLabel("/"));
        timePanel.add(month);
        timePanel.add(new JLabel("/"));
        timePanel.add(day);
        timePanel.add(new JLabel("   "));
        timePanel.add(hour);
        timePanel.add(new JLabel(":"));
        timePanel.add(minute);
        timePanel.add(new JLabel(":"));
        timePanel.add(second);
        timePanel.add(timeUpdate);
        timePanel.add(now);

        locationPanel.add(latitude);
        locationPanel.add(northSouth);
        locationPanel.add(longitude);
        locationPanel.add(westEast);
        locationPanel.add(locationUpdate);

        clickedList = new JPanel();
        clickedList.setPreferredSize(new Dimension(WIDTH - 30,200));
        clickedList.setLayout(new BoxLayout(clickedList,BoxLayout.PAGE_AXIS));

        JLabel Head = new JLabel(String.format("%-" + 4 + "s","No.") + String.format("%-" + 25 + "s","ProperName") + String.format("%-" + 18 + "s","Name") + String.format("%-" + 7 + "s","Ascen.")  + String.format("%-" + 7+ "s","Dec") +  String.format("%-" + 7  + "s","VisMag") + String.format("%-" + 7 + "s","Alt") + String.format("%-" + 5 + "s","Azi"));
        Head.setFont(new Font("Courier New", Font.PLAIN, 12));
        clickedList.add(Head);
        //clickedList.add(new JLabel(String.format("%-" + 5 + "s","No.") + String.format("%-" + 40 + "s","ProperName") + String.format("%-" + 25 + "s","Name") + String.format("%-" + 20 + "s","Right Ascension") + String.format("%-" + 10  + "s","VisMag") + String.format("%-" + 5 + "s","Alt") + String.format("%-" + 5 + "s","Az")));
//        clickedList.add(new JLabel(String.format("%-" + (30 - 10) + "s","ProperName")));
//        clickedList.add(new JLabel(String.format("%-" + (15 - 5) + "s","Name")));
//        clickedList.add(new JLabel(String.format("%-" + (35 - 16) + "s","Right Ascension")));
//        clickedList.add(new JLabel(String.format("%-" + (20 - 0) + "s","Visible Magnitude")));
//        clickedList.add(new JLabel(String.format("%-" + (15 - 0) + "s","Altitude")));
//        clickedList.add(new JLabel(String.format("%-" + (50 - 0) + "s","Azimuth")));
        clickedStars = new ArrayList<>();

//        clickedStars.add(new JLabel(sky[116].print()));
//        clickedStars.get(clickedStars.size()-1).setFont(new Font("Courier New", Font.PLAIN, 12));
//        clickedList.add(clickedStars.get(clickedStars.size()-1));




        inputs.add(timePanel);
        inputs.add(locationPanel);
        inputs.add(clickedList);

        this.add(inputs, BorderLayout.SOUTH);

        setVisible(true);
    }
    private void updateTime(){
        second.setText(String.format("%02d", time.getSecond()));
        minute.setText(String.format("%02d", time.getMinute()));
        hour.setText(String.format("%02d", time.getHour()));
        day.setText(String.format("%02d", time.getDay()));
        month.setSelectedIndex(time.getMonth());
        year.setText(String.format("%04d", time.getYear()));
    }
    private void updateLocation(){
        latitude.setText(String.format("%8.4f",location.getLatitude()));
        longitude.setText(String.format("%8.4f",location.getLongitude()));
        if(location.getWest()){
            westEast.setSelectedIndex(0);
        }
        else{
            westEast.setSelectedIndex(1);
        }
        if(location.getNorth()){
            northSouth.setSelectedIndex(0);
        }
        else{
            northSouth.setSelectedIndex(1);
        }
    }
    private void drawStars(){
        for(int i = 0; i < 300; i++) {
            map.add(sky[i]);
            this.revalidate();
        }
    }
    class TimeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                time.setYear(Integer.parseInt(year.getText()));
                time.setMonth(month.getSelectedIndex());
                time.setDate(Integer.parseInt(day.getText()));
                time.setHour(Integer.parseInt(hour.getText()));
                time.setMinute(Integer.parseInt(minute.getText()));
                time.setSecond(Integer.parseInt(second.getText()));

            }
            catch (Exception exception){
                 System.out.println("Please enter a valid Date/Time");
                 time.print();
            }
            time.update();
            updateTime();
            for(int i = 0; i < 300; i++){
                sky[i].update(location,time);
            }
            drawStars();
            revalidate();
            repaint();

        }
    }
    class LocationListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                double rawLong = Double.parseDouble(longitude.getText());
                double rawLat = Double.parseDouble(latitude.getText());
                if(westEast.getSelectedIndex() == 1) {
                    rawLong = -rawLong;
                }
                if(northSouth.getSelectedIndex() == 1) {
                    rawLat = - rawLat;

                }
                if(rawLat >= 90){
                    rawLat = 89.999999;
                }
                location.setLongitude(rawLong);
                location.setLatitude(rawLat);
            }
            catch(Exception exception){
                System.out.println("Please enter a valid Lat/Long");
                location.print();
            }
            updateLocation();
            for(int i = 0; i < 300; i++){
                sky[i].update(location,time);
            }
            drawStars();
            revalidate();
            repaint();

        }
    }

    class NowListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            time.now();
            time.update();
            updateTime();
            for(int i = 0; i < 300; i++){
                sky[i].update(location,time);
            }
            drawStars();
            revalidate();
            repaint();
        }
    }
    
    class ClickListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			for(int i = 0; i < 300; i++){
				if(sky[i].contains(e.getPoint())){
                    clickedStars.add(new JLabel(sky[i].print()));
                    clickedStars.get(clickedStars.size()-1).setFont(new Font("Courier New", Font.PLAIN, 12));
                    clickedList.add(clickedStars.get(clickedStars.size()-1));
                    if(clickedStars.size() > 10){
                        clickedList.remove(clickedStars.get(0));
                        clickedStars.remove(0);
                    }
                    revalidate();
                    repaint();
				}
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
    	
    }
}
