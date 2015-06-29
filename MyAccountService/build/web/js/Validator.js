/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* Функция, проверяющая валидность введённых значений id и value */
function validateForm()
{
    /* Значение поля id */
    var id = document.forms["account_service"]["id"].value;
    /* Значение поля value */
    var value = document.forms["account_service"]["value"].value;
    /* В переменную action записывается значение текущей выбранной операции 
    * getAmount или addAmount. */
    var action = document.forms["account_service"]["action"].value;
    /* Проверяется, введено ли число. */
    if (isNaN(id) || id == "")
    {
        alert("Incorrect value of 'Id field'");
        return false;
    }
    /* id не должно выходить за пределы Integer в Java */
    if (id > 2147483647)
    {
        alert("Value of 'Id field' misses the range");
        return false;
    }
    /* id должен быть положительным */
    if (id < 1)
    {
        alert("Id must be positive");
        return false;
    }
    /* В операции addAmount проверяется также значение поля value. */
    if (action == "AddAmount")
    {
         /* Проверяется, введено ли число. */
        if (isNaN(value) || value == "")
        {
            alert("Incorrect value of 'Value field'");
            return false;
        }
        /* value не должно выходить за пределы Long в Java */
        if (value > 9000000000000000000)
        {
            alert("Value of 'Value field' misses in the range");
            return false;
        }
    }
}
