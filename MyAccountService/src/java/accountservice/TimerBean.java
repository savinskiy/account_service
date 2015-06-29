/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package accountservice;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author Savin
 */

/* Усовершенствованный класс для работы с сервисом. Позволяет получить статистику
 * времени выполнения методов
*/
@ManagedBean(name = "timerBean")
public class TimerBean extends MyServiceBean
{
    private static int CounterOfGet, CounterOfAdd;
    private static long summary_time_get, summary_time_add;
    private static long last_result_time_get, last_result_time_add;
    
    /* Получение данных о количестве запросов GetAmount */
    public int getCounterOfGet ()
    {
        return CounterOfGet;
    }
            
    /* Получение данных о количестве запросов AddAmount */
    public int getCounterOfAdd () 
    {
        return CounterOfAdd;
    }
    
    /* Получение данных о среднем времени запроса GetAmount */
    public long getAverageTimeForGet()
    {
        long average_time_get = 0;
        /* Учитываем деление на ноль */
        if (CounterOfGet != 0)
            /* Делим суммарное время на количество запросов, получаем среднее время */
            average_time_get = summary_time_get / CounterOfGet;
        else
            average_time_get = 0;
        return average_time_get;
    }
    
    /* Получение данных о среднем времени запроса AddAmount */
    public long getAverageTimeForAdd()
    {
        long average_time_add = 0;
        /* Учитываем деление на ноль */
        if (CounterOfAdd != 0)
            /* Делим суммарное время на количество запросов, получаем среднее время */
            average_time_add = summary_time_add / CounterOfAdd;
        else 
            average_time_add = 0;
        return average_time_add;
    }
    
    /* Получение данных о потенциально возможном количестве запросов GetAmount
    в секунду */
    public long getNumberOfGetQueriesPerSec ()
    {
        long NumberOfGetQueriesPerSec = 0;
        /* Учитываем деление на ноль */
        if (CounterOfGet != 0) 
        {
            /* Делим суммарное время на количество запросов, получаем среднее время */
            long average_time_get = summary_time_get / CounterOfGet;
            /* Переводим время в секунды:
             * 1 с = 10^9 нс = 1000 * 1000 * 1000 нс 
             * Получаем количество запросов в секунду. */
            if (average_time_get != 0)
                NumberOfGetQueriesPerSec = 1000 * 1000 * 1000 / average_time_get;
            else 
                NumberOfGetQueriesPerSec = 0;
        } 
        else 
            NumberOfGetQueriesPerSec = 0;
        return NumberOfGetQueriesPerSec;
    }
    
    /* Получение данных о потенциально возможном количестве запросов AddAmount
    в секунду */
    public long getNumberOfAddQueriesPerSec ()
    {
        long NumberOfAddQueriesPerSec = 0;
        /* Учитываем деление на ноль */
        if (CounterOfAdd != 0) 
        {
            /* Делим суммарное время на количество запросов, получаем среднее время */
            long average_time_add = summary_time_add / CounterOfAdd;
             /* Переводим время в секунды:
             * 1 с = 10^9 нс = 1000 * 1000 * 1000 нс 
             * Получаем количество запросов в секунду. */
            if (average_time_add != 0)
                NumberOfAddQueriesPerSec = 1000 * 1000 * 1000 / average_time_add;
            else
                NumberOfAddQueriesPerSec = 0;
        }
        else 
            NumberOfAddQueriesPerSec = 0;
        return NumberOfAddQueriesPerSec;
    }
    
     /* Получение данных о длительности последнего запроса GetAmount */
    public long getLastResultTimeGet ()
    {
        return last_result_time_get;
    }
    
    /* Получение данных о длительности последнего запроса AddAmount*/
    public long getLastResultTimeAdd ()
    {
        return last_result_time_add;
    }
        
    /* Функция сброса статистики */
    public static void ClearStat ()
    {
        CounterOfGet = 0;
        CounterOfAdd = 0;
        summary_time_get = 0;
        summary_time_add = 0;
        last_result_time_get = 0;
        last_result_time_add = 0;
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
        /* Увеличиваем счетчик запросов. */
        CounterOfGet++;
        long timeout= System.nanoTime();
        /* Вызываем метод родительского класса */
        Long result = super.getAmount(id);
        /* Получаем длительность времени выполнения метода и записываем ее 
         * в переменную last_result_time_get. */
        timeout = System.nanoTime() - timeout;
        last_result_time_get = timeout;
        /* Записываем суммарное время выполнения метода */
        summary_time_get += last_result_time_get;
        return result;
    }
    
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
        /* Увеличиваем счетчик запросов. */
        CounterOfAdd++;
        long timeout= System.nanoTime();
        /* Вызываем метод родительского класса */
        super.addAmount(id, value);
        /* Получаем длительность времени выполнения метода и записываем ее 
         * в переменную last_result_time_add. */
        timeout = System.nanoTime() - timeout;
        last_result_time_add = timeout;
        /* Записываем суммарное время выполнения метода */
        summary_time_add += last_result_time_add;
    }
    
}
