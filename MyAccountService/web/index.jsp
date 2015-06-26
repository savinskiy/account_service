<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://java.sun.com/jsf/html">
    <%-- 
        Document   : index
        Created on : 07.09.2014, 19:32:46
        Author     : Savin
    --%>




    <%@page contentType="text/html" pageEncoding="UTF-8"%>
    <!DOCTYPE html>
    <html> 
        <head>
            <script type="text/javascript" src="js/FieldsShowHide.js"></script>
            <script type="text/javascript" src="js/StatsShowHide.js"></script>
            <script type="text/javascript" src="js/Validator.js"></script>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Account Service</title>
        </head>
        <body>
            <div id="operations" style="float:left; padding-right: 100px" >
                <table border="0">
                    <thead>
                        <tr>
                            <th>
                                <h1> Welcome to the Account Service! </h1>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                                <b> Choose the operation: </b> <br/>
                                <i>GetAmount</i> - Retrieves current balance for specified id <br/>
                                <i>AddAmount</i> - Increases balance for specified id
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <!-- Форма с полями: выбор операции, id, value -->
                                <!-- Валидатор проверяет поля id, value -->
                                <form name="account_service" action="index.jsp" method="POST" >
                                    <strong>Select the operation:</strong>
                                    <!-- fieldsShowHide() - aункция, которая прячет неиспользуемые поля в зависимости от
                                    выбора операции. Выполняется при изменении элемента с выбором операции -->
                                    <select name="action" onchange="fieldsShowHide()">
                                        <option value="GetAmount">GetAmount</option>
                                        <option value="AddAmount">AddAmount</option>
                                    </select>

                                    <table border="0">
                                        <tbody>
                                            <tr id="enter_id">
                                                <td width="100">Enter id</td>
                                                <td>
                                                    <!-- Поле id, ввод данных ограничен регулярным выражением-->
                                                    <input type="text" name="id" maxlength="10"  oninvalid="alert('Enter only digits from 0 to 9')" style="text-align: right"/>
                                                </td>
                                            </tr>
                                            <tr id="enter_value">
                                                <td width="100">Enter value</td>
                                                <td>
                                                     <!-- Поле value, ввод данных ограничен регулярным выражением-->
                                                    <input type="text" name="value" maxlength="20" pattern="^[-+]?[0-9]*$" oninvalid="alert('Enter only digits from 0 to 9 with plus or minus sign')"  style="text-align: right"/>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <input type="submit" value="submit" name="submit"/>
                                </form>
                                <%-- Функция, которая прячет неиспользуемые поля в зависимости от
                                выбора операции. Выполняется при загрузке страницы. --%>
                                <script type="text/javascript"> fieldsShowHide();</script>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <jsp:useBean id="timerBean" class="accountservice.TimerBean">
                     <%-- Скрипт выполняет запрашиваемую функцию getAmount или 
                     addAmount и выводит справочную информацию. --%>
                    <h3>   
                        <c:catch var ="errormsg">
                            <c:choose>
                                <c:when test="${param.action == 'GetAmount'}">
                                    Your current balance is: <c:out value="${timerBean.getAmount(param.id)}"/>
                                </c:when>
                                <c:when test="${param.action == 'AddAmount'}">
                                    ${timerBean.addAmount(param.id, param.value)}
                                    Your money has been successfully added to the current balance!
                                </c:when>
                                <c:otherwise>
                                    ACCOUNT SERVICE 2015
                                </c:otherwise>
                            </c:choose>  
                        </c:catch>
                        <c:if test = "${errormsg != null}">
                          <p>There has been an exception raised in the above
                          previous operation: ${errormsg}</p>
                          <p>There is an exception: ${errormsg.message}</p>
                        </c:if>        
                    </h3>
                </div>
                <div id="stats">
                    <c:choose>
                         <%-- Выполняется сброс статистики --%>
                        <c:when test="${param.stats == 'reset'}">
                            ${timerBean.ClearStat()}
                        </c:when>
                         <%-- Установка вспомогательного атрибута stats_hidden,
                         который будет использоваться, чтобы определить, нужно
                         ли показывать статистику --%>
                        <c:when test="${param.stats == 'show'}">
                            <c:set var="stats_hidden" value="false" scope="session" />
                        </c:when>
                        <c:when test="${param.stats == 'hide'}">
                            <c:set var="stats_hidden" value="true" scope="session" />
                        </c:when>
                    </c:choose>  

                    <h2>Account Servise Statistics</h2>
                    <h3>Statistics of the queries:</h3>
                     <!-- Форма с выбором функций: показывать/спрятать 
                     статистику; сбросить статистику -->
                    <form action="index.jsp" name="statistics" method="POST">
                        <select name="stats" size="1">
                             <!-- Значение и текст опции Hide меняется в зависимости от того,
                             показана ли в данный момент  -->
                            <option id="stats_show_hide" value="hide">Hide</option>
                            <option value="reset"> Reset</option>
                        </select>
                        <input type="submit" value="submit" name="submit"/>
                    </form>

                     <!-- Здесь выводится статистика сервера -->
                    <table id="stats_table" border="1">
                        <tbody>                
                            <tr>
                                <th>Number of 'Get Amount' queries: </th>
                                <td><c:out value="${timerBean.counterOfGet}"/></td>
                            </tr>
                            <tr>
                                <th>Average time of 'Get Amount' query: </th>
                                <td><c:out value="${timerBean.averageTimeForGet}"/> ns</td>
                            </tr>
                            <tr>
                                <th>Number of possible 'Get Amount' queries per second: </th>
                                <td><c:out value="${timerBean.numberOfGetQueriesPerSec}"/></td>
                            </tr>
                            <tr>
                                <th>Previous time 'Get Amount' query: </th>
                                <td><c:out value="${timerBean.lastResultTimeGet}"/> ns</td>
                            </tr>
                            <tr>
                                <th>Number of 'Add Amount' queries: </th>
                                <td><c:out value="${timerBean.counterOfAdd}"/></td>
                            </tr>
                            <tr>
                                <th>Average time of 'Add Amount' query: </th>
                                <td><c:out value="${timerBean.averageTimeForAdd}"/> ns</td>
                            </tr>
                            <tr>
                                <th>Number of possible 'Add Amount' queries per second:</th>
                                <td><c:out value="${timerBean.numberOfAddQueriesPerSec}"/></td>
                            </tr>
                            <tr>
                                <th>Previous time 'Add Amount' query: </th>
                                <td><c:out value="${timerBean.lastResultTimeAdd}"/> ns</td>
                            </tr>
                        </tbody>
                    </table>

                     <%-- Скрипт который показывает/прячет статистику 
                     в зависимости от значения вспомогательного 
                     атрибута сессии stats_hidden --%>
                    <script>
                        var stats_hidden = ${sessionScope.stats_hidden};
                        statsShowHide(stats_hidden);
                    </script>        
                </jsp:useBean>
            </div>
        </body>
    </html>
