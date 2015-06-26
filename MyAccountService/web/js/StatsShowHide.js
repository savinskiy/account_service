/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* Функция, которая показывает или скрывает статистику в зависимости от 
 * значения переданного аргумента stats_hidden */
function statsShowHide(stats_hide)
{  
    /* Опция выбора операции (показать/убрать статистику) во второй
     * форме. Ёе значение и текст будут меняться в зависимости от того,
     * показана ли в данный момент статистика.  */
    var stats_show_hide = document.getElementById("stats_show_hide");
    /* таблица stats_table */
    var table = document.getElementById("stats_table");
    
    /* Ветка, в которой надо спрятать статистику. */
    if (stats_hide == true)
    {
        /* спрятать таблицу со статистикой */
        table.style.display = "none";
        /* меняем значение операции 'спрятать' на 'показать' */
        stats_show_hide.value = "show";
        stats_show_hide.text = "Show";
    }
     /* Ветка, в которой надо показать статистику. */
    else
    {
         /* показать таблицу со статистикой */
        table.style.display = "block";
        /* меняем значение операции 'показать' на 'спрятать' */
        stats_show_hide.value = "hide";
        stats_show_hide.text = "Hide";     
    }
}  