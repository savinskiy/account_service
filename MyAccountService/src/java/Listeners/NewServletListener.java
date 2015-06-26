package Listeners;


import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

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
public class NewServletListener implements HttpSessionListener 
{

    /* Метод, выполняющийся при создании сессии */
    @Override
    public void sessionCreated(HttpSessionEvent se) 
    {
        /* Установить в данной сессии вспомогательный атрибут stats_hidden
         * в значение false, чтобы изначально показывать статистику сервера */
        se.getSession().setAttribute("stats_hidden", "false");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {     
    }
}
