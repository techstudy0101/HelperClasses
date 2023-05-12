package com.techstudy.SqsDemo.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Monitor extends Thread{

    private List<Long> producerTimes = new ArrayList<>();
    private List<Long> consumerTimes = new ArrayList<>();
    private List<Long> deleteTimes = new ArrayList<>();

    public static Monitor monitor = new Monitor();

    public static Monitor getMonitor() {
        return monitor;
    }

    private Monitor(){

    }

    public synchronized void addProducerTime(long time){
        producerTimes.add(time);
    }

    public synchronized void addConsumerTime(long time){
        consumerTimes.add(time);
    }

    public synchronized void addDeleteTime(long time){
        deleteTimes.add(time);
    }


    public String getTimes(List<Long> timeMetrics){
        String str = ",";
       String times = timeMetrics
                .stream()
                .map(it -> String.valueOf(it))
                .collect(Collectors.joining(","));
        return times;
    }

    @Override
    public void run() {
        while (true){
            try{
                Thread.sleep(1000);
                String producerTimesInStr = getTimes(producerTimes);
                String consumerTimesInStr = getTimes(consumerTimes);
                String deleteTimesInStr = getTimes(deleteTimes);

                System.out.println("metric \n " +
                        "producerTimes = " + producerTimesInStr + "\n" +
                        "consumerTimes = " + consumerTimesInStr + "\n" +
                        "deleteTimes = " + deleteTimesInStr + "\n"
                );
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
