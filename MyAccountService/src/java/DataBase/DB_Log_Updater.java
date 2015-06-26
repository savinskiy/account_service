package DataBase;

import java.util.HashMap;
import java.util.Date;
import java.text.DateFormat;
import java.io.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/** Класс, описывающий действия запланированной задачи
 *
 * @author Savin
 */
class ReminderTask extends Thread 
{
    /* Запуск задачи */
    public ReminderTask()
    {
        start();
    }
    
    /* Получение статистики сервера */
    private String GetServerStats ()
    {
        String result;
        accountservice.TimerBean statistics = new accountservice.TimerBean();
        result = "\nNumber of 'Get Amount' queries:" + statistics.getCounterOfGet();
        result += "\nAverage time of 'Get Amount' query:" + statistics.getAverageTimeForGet();
        result += "\nNumber of possible 'Get Amount' queries per second:" + statistics.getNumberOfGetQueriesPerSec();
        result += "\nPrevious time 'Get Amount' query:" + statistics.getLastResultTimeGet();
        result += "\nNumber of 'Add Amount' queries:" + statistics.getCounterOfAdd();
        result += "\nAverage time of 'Add Amount' query:" + statistics.getAverageTimeForAdd();
        result += "\nNumber of possible 'Add Amount' queries per second:" + statistics.getNumberOfAddQueriesPerSec();
        result += "\nPrevious time 'Add Amount' query:" + statistics.getLastResultTimeAdd();
        result += "\n\n\n";
        return result;
    }
    
    /* Запись статистики stats в файл */
    private void WriteToLog (String stats)
    {
        PrintWriter out = null;
        /* Имя файла */
        String log_filename = "ServerStats.log";
        try 
        {
            //Создаём объект файла
            File flt = new File(log_filename);
            out = new PrintWriter(new BufferedWriter(new FileWriter(flt)));
            /* Записываем данные в файл */
            out.print(stats);
            out.flush();
            System.out.println("Output is generated in a file " + log_filename);
        }
        /* В случае неудачи записи в файл выводим сообщение об ошибке */
        catch (IOException ex) 
        {
            System.err.println("Can't write to file" + log_filename);
        }
        finally 
        {
            out.close();
        }
    }
 
    /* Выполняющаяся задача */
    public void run()
    {
        /* Получение списка клиентов и загрузка их в базу данных */
        HashMap<Integer, Long> clients = accountservice.Clients.getClients();
        DataBaseGetAndLoad.loadDataToDB(clients);
        System.out.println("Database updated");
        
        /* Текущее время */
        Date curTime = new Date();
        DateFormat dtfrm = DateFormat.getDateInstance();
        String dateTime = dtfrm.format(curTime);
        String statistics = "Current date and time is: " + dateTime;
        statistics += "\nServer statistics:";
        /* Получение статистики сервера */
        statistics += GetServerStats();
        /* Запись статистики в файл */
        WriteToLog(statistics);
    }
}
 
/* Класс - планировщик задач */
 public class DB_Log_Updater extends Thread 
 {
    public static final int STOP = 0;
    public static final int RUN = 1;
    public static final int WAIT = 2;
 
    protected int state = WAIT;
    protected long delay;
 
    public DB_Log_Updater(long seconds) {
        delay = seconds * 1000;
        state = RUN;
        start();
    }
 
    public synchronized void setState(int state) {
        System.out.println("SetState:" + state);
        this.state = state;
        if (state == RUN) {
            notify();
        }
    }
 
    public synchronized int checkState() {
        while (state == WAIT) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        return state;
    }
 
    /* Запуск планировщика */
    public void run() {
        while (checkState() != STOP) {
            try {
                /* Остановка планировщика */
                this.sleep(delay);
            } catch (InterruptedException e) {
            }
            /* Создание новой задачи */
            new ReminderTask();
        }
    }
 
}