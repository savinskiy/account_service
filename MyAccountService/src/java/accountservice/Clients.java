/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountservice;

import java.util.HashMap;
import javax.faces.bean.ApplicationScoped;
import java.io.Serializable;

/**
 *
 * @author Savin
 */

/* Класс для работы с клиентами. */
@ApplicationScoped
public class Clients implements Serializable 
{
   // синхронизированный HashTable вместо HashMap
    /* Переменная в которой хранятся данные обо всех клиентах. */
    private static HashMap<Integer, Long> clients = new HashMap<Integer, Long>();    
    
    /* Получение списка клиентов. */
    public static synchronized HashMap<Integer, Long> getClients ()
    {
        return clients;
    }
    
    /* Установка списка клиентов. */
    public static synchronized void setClients (HashMap<Integer, Long> clients)
    {
        Clients.clients = clients;
    }
}
