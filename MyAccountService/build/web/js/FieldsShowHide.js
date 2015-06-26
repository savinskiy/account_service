/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* Функция, которая прячет неиспользуемые поля в зависимости от  
 выбора операции, а именно: прячет поле value при выборе функции getAmount */
function fieldsShowHide ()
{
    /* поле ввода id */
    var obj_id = document.getElementById("enter_id");
    /* поле ввода value */
    var obj_value = document.getElementById("enter_value");
    /* В переменную action записывается значение текущей выбранной операции 
     * getAmount или addAmount. */
    var action = document.getElementsByName("action")[0].value;
    /* проверяется, какая операция выбрана */
    if (action == "AddAmount")
    {
        obj_id.style.display = "block"; //Показываем элемент id
        obj_value.style.display = "block"; //Показываем элемент value
    }
    else if (action == "GetAmount")
    {
        obj_id.style.display = "block"; //Показываем элемент id
        obj_value.style.display = "none"; //Скрываем элемент value
    }
    /* ветка для других потенциальных операций в случае 
     * дальнейшего развития проекта */
    else
    {
        obj_id.style.display = "none"; //Скрываем элемент id
        obj_value.style.display = "none"; //Скрываем элемент value
    }
}  