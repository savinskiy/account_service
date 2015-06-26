package Listeners;

import java.util.HashMap;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Web application lifecycle listener.
 *
 * @author Savin
 */
public class MyServletContextListener implements ServletContextListener 
{
    private HashMap<Integer, Long> clients;
    
    /* Метод, выполняющийся при запуске приложения */
    @Override
    public void contextInitialized(ServletContextEvent sce) 
    {
        /* Установка статистики сервера в ноль */
        accountservice.TimerBean.ClearStat();
        /* Получение текущей базы данных из файла со свойствами */
        DataBase.DataBaseGetAndLoad.DataBaseInitialization();
        /* Получение данных из базы */
        clients = DataBase.DataBaseGetAndLoad.getDataFromDB();
        /* Запись данных из базы в класс для работы с клиентами */
        accountservice.Clients.setClients(clients);
        /* 30-минутная задержка */
        long delay_in_seconds = 30 * 60;
        /* Установка планировщика с целью каждые 30 минут сохранять данные
         * в базу и записывать в жрунал статистику сервера */
        new DataBase.DB_Log_Updater(delay_in_seconds);
    }

    /* Метод, выполняющийся при завершении работы приложения */
    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
        /* Получение списка клиентов */
        clients = accountservice.Clients.getClients();
        /* Сохранение данных в базу */
        DataBase.DataBaseGetAndLoad.loadDataToDB(clients);
    }
}
