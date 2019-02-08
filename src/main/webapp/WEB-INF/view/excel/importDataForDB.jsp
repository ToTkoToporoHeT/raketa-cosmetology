<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="springForm" uri="http://www.springframework.org/tags/form"  %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags/"%>
<!DOCTYPE html>

<page:mainTamplate>
    <jsp:attribute name="currentPage">importData</jsp:attribute>
    <jsp:attribute name="title">Импорт данных</jsp:attribute>

    <jsp:body>
        <c:url var="actionURL" value="/import/uploadAndImport"/>
        <h1>Импорт материалов и услуг из Excel в базу данных</h1>
        <c:if test="${errors != null}">
            <div class="row">
                <fieldset class="col-lg-12 form-group">
                    <c:forEach items="${errors}" var="error">
                        <div class="alert alert-danger" 
                             style="padding: 4px; margin-bottom: 3px;" 
                             role="alert">
                            ${error}
                        </div>
                    </c:forEach>
                </fieldset>
            </div>
        </c:if>
        <springForm:form cssClass="form-horizontal" action="${actionURL}" method="POST" commandName="excelFile" enctype="multipart/form-data">
            <div class="row">
                <fieldset class="col-sm-12 col-lg-12 form-group">
                    <legend></legend>
                    <label for="file">Импортируемый файл</label>
                    <springForm:input id="file" cssClass="file" type="file" path="mpFile" 
                                      accept="application/x-msexcel,
                                      application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,
                                      application/vnd.ms-excel.sheet.macroEnabled.12"/>
                    <p class="help-block">Выберите файл для импорта</p>
                    <div class="radio">
                        <label>
                            <input name="fileType" type="radio" value="services"/>
                            Импортировать сервисы
                        </label>
                    </div>
                    <div class="radio">
                        <label>
                            <input name="fileType" type="radio" value="materials" checked="true"/>
                            Импортировать материалы
                        </label>
                    </div>
                    <div class="row">
                        <fieldset class="col-sm-6 col-lg-6">
                            <legend class="lead">Расоложение информации об услугах</legend>
                            <div class="form-group">
                                <div class="col-lg-12">                                    
                                    <label for="rowStart" class="control-label">Строка начала</label>
                                    <springForm:errors cssClass="label label-danger" path="servicesRowColInfo.rowStartData"/>
                                    <springForm:input id="rowStart" path="servicesRowColInfo.rowStartData" cssClass="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-lg-12">
                                    <label for="rowEnd" class="control-label">Строка конца</label>
                                    <springForm:errors cssClass="label label-danger" path="servicesRowColInfo.rowEndData"/>
                                    <springForm:input id="rowEnd" path="servicesRowColInfo.rowEndData" cssClass="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-lg-12">
                                    <label for="name" class="control-label">Столбец с наименованиями</label>
                                    <springForm:errors cssClass="label label-danger" path="servicesRowColInfo.name"/>
                                    <springForm:input id="name" path="servicesRowColInfo.name" cssClass="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-lg-12">
                                    <label for="cost" class="control-label">Столбец со стоимостью</label>
                                    <springForm:errors cssClass="label label-danger" path="servicesRowColInfo.price"/>
                                    <springForm:input id="cost" path="servicesRowColInfo.price" cssClass="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-lg-12">
                                    <label for="costFF" class="control-label">Столбец со стоимостью для <abbr title="Иностранных граждан">ИГ</abbr></label>
                                    <springForm:errors cssClass="label label-danger" path="servicesRowColInfo.priceFF"/>
                                    <springForm:input id="costFF" path="servicesRowColInfo.priceFF" cssClass="form-control"/>
                                </div>
                            </div>
                        </fieldset>
                        <div class="form-group">
                            <fieldset class="col-sm-6 col-lg-6">
                                <legend class="lead">Расоложение информации о материалах</legend>
                                <div class="form-group">
                                    <div class="col-lg-12">                                    
                                        <label for="mRowStart" class="control-label">Строка начала</label>
                                        <springForm:errors cssClass="label label-danger" path="materialRowColInfo.rowStartData"/>
                                        <springForm:input id="mRowStart" path="materialRowColInfo.rowStartData" cssClass="form-control"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <label for="mRowEnd" class="control-label">Строка конца</label>
                                        <springForm:errors cssClass="label label-danger" path="materialRowColInfo.rowEndData"/>
                                        <springForm:input id="mRowEnd" path="materialRowColInfo.rowEndData" cssClass="form-control"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <label for="mName" class="control-label">Столбец номера в отчете</label>
                                        <springForm:errors cssClass="label label-danger" path="materialRowColInfo.numberInReport"/>
                                        <springForm:input id="mNumberInReport" path="materialRowColInfo.numberInReport" cssClass="form-control"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <label for="mName" class="control-label">Столбец номенклатурного номера</label>
                                        <springForm:errors cssClass="label label-danger" path="materialRowColInfo.itemNumber"/>
                                        <springForm:input id="mItemNumber" path="materialRowColInfo.itemNumber" cssClass="form-control"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <label for="mName" class="control-label">Столбец с наименованиями</label>
                                        <springForm:errors cssClass="label label-danger" path="materialRowColInfo.name"/>
                                        <springForm:input id="mName" path="materialRowColInfo.name" cssClass="form-control"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <label for="mUnit" class="control-label">Столбец с единицами измерения</label>
                                        <springForm:errors cssClass="label label-danger" path="materialRowColInfo.unit"/>
                                        <springForm:input id="mUnit" path="materialRowColInfo.unit" cssClass="form-control"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <label for="mCount" class="control-label">Столбец с количеством</label>
                                        <springForm:errors cssClass="label label-danger" path="materialRowColInfo.count"/>
                                        <springForm:input id="mCount" path="materialRowColInfo.count" cssClass="form-control"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <label for="mCost" class="control-label">Столбец c ценой</label>
                                        <springForm:errors cssClass="label label-danger" path="materialRowColInfo.price"/>
                                        <springForm:input id="mCost" path="materialRowColInfo.price" cssClass="form-control"/>
                                    </div>
                                </div>
                            </fieldset>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <springForm:button class="btn btn-primary" type="submit">
                            Импортировать
                        </springForm:button>
                    </div>
                </fieldset>
            </div>
        </springForm:form>
    </jsp:body>
</page:mainTamplate>
