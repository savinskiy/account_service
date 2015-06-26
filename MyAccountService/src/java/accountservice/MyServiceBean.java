/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountservice;

import java.util.HashMap;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Savin
 */

/* Интерфейс работы сервиса */
interface AccountService
{
    /**
     * Retrieves current balance or zero if addAmount() method was not called
     * before for specified id
     *
     * @param id balance identifier
     */
    Long getAmount(Integer id);

    /**
     * Increases balance or set if addAmount() method was called first time
     *
     * @param id balance identifier
     * @param value positive or negative value, which must be added to current
     * balance
     */
    void addAmount(Integer id, Long value);
}

/* Класс для работы с сервисом */
@ManagedBean(name = "myserviceBean")
public class MyServiceBean implements AccountService 
{
    private HashMap <Integer, Long> clients;    

    /** 
     * Операция AddAmount увеличивает баланс на value для человека с номером id
     *
     * @param id идентификатор баланса
     * @param value положительное или отрицательное число, которые должно быть 
     * прибавлено к текущему балансу
    */
    @Override
    public void addAmount(Integer id, Long value) 
    {
        /* Получаем текущий список всех клиентов */
        clients = Clients.getClients();
        Long new_value;
        /* Если клиент с таким id есть в базе добавляем значение value */
        if (clients.containsKey(id)) 
        {       
            new_value = clients.get(id) + value;
            clients.put(id, new_value);     
            Clients.setClients(clients);
        } 
        /* Если для данного id метод вызывается первый раз записываем значение */ 
        else 
        {        
            clients.put(id, value);
            Clients.setClients(clients);
        }
    }

     /**
     * Получает текущий баланс человека с номером id или нулевое значение, 
     * если метод addAmount() еще не вызывался
     *
     * @param id идентификатор баланса
     */
    @Override
    public Long getAmount(Integer id) 
    {
        /* Получаем список всех клиентов. */
        clients = Clients.getClients();
        final Long zero = 0l;
        /* Если клиент с данным id есть в базе выдаем его значение value */
        if (clients.containsKey(id))
        {
            
            return clients.get(id);
        }
        /* Иначе выдаем 0  */
        else 
        {
           
            return zero;
        }
    }
}
