package com.hugqq.task;

import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo {
    /**
     * Timer 后台调度任务的线程只有一个，所以导致任务是阻塞运行的，一旦其中一个任务执行周期过长将会影响到其他任务。
     * Timer 本身没有捕获其他异常（只捕获了 InterruptedException），一旦任务出现异常（比如空指针）将导致后续任务不会被执行。
     * <p>
     * 多线程并行处理定时任务时，Timer运行多个TimeTask时，只要其中之一没有捕获抛出的异常，其它任务便会自动终止运行，使用ScheduledExecutorService则没有这个问题。
     *
     * @param args
     */
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int i = 1;
                System.out.println(i + "s");
                i++;
            }
        }, 1, 1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("1s");
            }
        }, 1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("2s");
            }
        }, 2000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("3s");
            }
        }, 3000);
    }
}
