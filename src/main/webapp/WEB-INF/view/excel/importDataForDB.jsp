<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="springForm" uri="http://www.springframework.org/tags/form"  %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags/"%>
<!DOCTYPE html>

<page:mainTamplate>
    <jsp:attribute name="currentPage">importData</jsp:attribute>
    <jsp:attribute name="title">Импорт данных</jsp:attribute>

    <jsp:body>
        <h1>Импорт материалов и услуг из Excel в базу данных</h1>
        <springForm:form cssClass="form-horizontal" action="/import/uploadAndImport" method="POST" commandName="excelFile" enctype="multipart/form-data">
            <div class="row">
                <fieldset class="col-sm-12 col-lg-12 form-group">
                    <legend></legend>
                    <label for="file">Импортируемый файл</label>
                    <springForm:input id="file" cssClass="file" type="file" path="mpFile" 
                                      accept="application/x-msexcel,
                                      application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,
                                      application/vnd.ms-excel.sheet.macroEnabled.12"/>
                    <p class="help-block">Выберите файл для импорта</p>
                    <div class="row">
                        <fieldset class="col-sm-6 col-lg-6">
                            <legend class="lead">Расоложение информации об услугах</legend>
                            <div class="form-group">
                                <div class="col-lg-12">                                    
                                    <label for="rowStart" class="control-label">Строка начала</label>
                                    <springForm:errors cssClass="label label-danger" path="serviceDataMap.rowStartData"/>
                                    <springForm:input id="rowStart" path="serviceDataMap.rowStartData" cssClass="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-lg-12">
                                    <label for="rowEnd" class="control-label">Строка конца</label>
                                    <springForm:errors cssClass="label label-danger" path="serviceDataMap.rowEndData"/>
                                    <springForm:input id="rowEnd" path="serviceDataMap.rowEndData" cssClass="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-lg-12">
                                    <label for="name" class="control-label">Столбец с наименованиями</label>
                                    <springForm:errors cssClass="label label-danger" path="serviceDataMap.name_ColumnNumber"/>
                                    <springForm:input id="name" path="serviceDataMap.name_ColumnNumber" cssClass="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-lg-12">
                                    <label for="cost" class="control-label">Столбец со стоимостью</label>
                                    <springForm:errors cssClass="label label-danger" path="serviceDataMap.cost_ColumnNumber"/>
                                    <springForm:input id="cost" path="serviceDataMap.cost_ColumnNumber" cssClass="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-lg-12">
                                    <label for="costFF" class="control-label">Столбец со стоимостью для <abbr title="Иностранных граждан">ИГ</abbr></label>
                                    <springForm:errors cssClass="label label-danger" path="serviceDataMap.costFF_ColumnNumber"/>
                                    <springForm:input id="costFF" path="serviceDataMap.costFF_ColumnNumber" cssClass="form-control"/>
                                </div>
                            </div>
                        </fieldset>
                        <div class="form-group">
                            <fieldset class="col-sm-6 col-lg-6">
                                <legend class="lead">Расоложение информации о материалах</legend>
                                <div class="form-group">
                                    <div class="col-lg-12">                                    
                                        <label for="mRowStart" class="control-label">Строка начала</label>
                                        <springForm:errors cssClass="label label-danger" path="materilDataMap.rowStartData"/>
                                        <springForm:input id="mRowStart" path="materilDataMap.rowStartData" cssClass="form-control"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <label for="mRowEnd" class="control-label">Строка конца</label>
                                        <springForm:errors cssClass="label label-danger" path="materilDataMap.rowEndData"/>
                                        <springForm:input id="mRowEnd" path="materilDataMap.rowEndData" cssClass="form-control"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <label for="mName" class="control-label">Столбец с наименованиями</label>
                                        <springForm:errors cssClass="label label-danger" path="materilDataMap.name_ColumnNumber"/>
                                        <springForm:input id="mName" path="materilDataMap.name_ColumnNumber" cssClass="form-control"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <label for="mCost" class="control-label">Столбец со стоимостью</label>
                                        <springForm:errors cssClass="label label-danger" path="materilDataMap.cost_ColumnNumber"/>
                                        <springForm:input id="mCost" path="materilDataMap.cost_ColumnNumber" cssClass="form-control"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <label for="mUnit" class="control-label">Столбец с единицами измерения</label>
                                        <springForm:errors cssClass="label label-danger" path="materilDataMap.unit_ColumnNumber"/>
                                        <springForm:input id="mUnit" path="materilDataMap.unit_ColumnNumber" cssClass="form-control"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <label for="mCount" class="control-label">Столбец с количеством</label>
                                        <springForm:errors cssClass="label label-danger" path="materilDataMap.count_ColumnNumber"/>
                                        <springForm:input id="mCount" path="materilDataMap.count_ColumnNumber" cssClass="form-control"/>
                                    </div>
                                </div>
                            </fieldset>
                        </div>
                    </div>
                    <div class="row">
                        <fieldset class="col-lg-6 form-group">

                        </fieldset>
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
