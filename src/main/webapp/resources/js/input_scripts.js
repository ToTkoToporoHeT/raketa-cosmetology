/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function addTelnumber() {
    var idStr = $('#telephone_numbers').find('div.tel-number:last').attr('id');
    var index;
    if (idStr) {
        index = parseInt(idStr.slice(10)) + 1;
        if (index == NaN)
            index = 0;
    } else {
        index = 0;
    }
    $('div#telephone_numbers').append(
            '<div id="tel_number' + index + '" class="form-group tel-number">\n\
                <input id="telephonenumbersList' + index + '.id" name="telephonenumbersList[' + index + '].id" type="hidden" value="">\n\
                <div class="input-group">\n\
                    <input id="tel' + index + '" name="telephonenumbersList[' + index + '].telephoneNumber" class="form-control" type="text" value="">\n\
                    <span class="input-group-btn">\n\
                        <button class="btn btn-default" onclick="deleteField(' + index + ')" type="button">\n\
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>\n\
                        </button>\n\
                    </span>\n\
                </div>\n\
                <input id="telephonenumbersList' + index + '.customer" name="telephonenumbersList[' + index + '].customer" type="hidden" value="">\n\
             </div>');
}

function deleteField(id) {
    $('div#tel_number' + id).remove();
}

function addVisitDate(postURL) {
    var index = $('#visitDates>.visit-dates').last().index() + 1;

    //получение текущей даты в формате yyyy-MM-dd
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd;
    }
    if (mm < 10) {
        mm = '0' + mm;
    }
    today = yyyy + '-' + mm + '-' + dd;

    $.post(postURL + '/add',
            {
                arrIndex: index,
                visit_date: today
            },
            appendVisitDate(postURL, index, today));
}

function editVisitDate(postURL, index) {
    var visit_date = $('#visitDates').find('.visit-dates').eq(index).find(':input[type=date]').val();
    $.post(postURL + '/edit',
            {
                arrIndex: index,
                visit_date: visit_date
            });
}

function deleteVisitDate(postURL, index) {
    $.post(postURL + '/delete',
            {
                arrIndex: index,
                visit_date: null
            },
            function () {
                $('#visitDates>.visit-dates').each(function (curIndex) {
                    if (curIndex > index) {
                        $(this).find(':input[type!=button]').each(function (i, elem) {
                            changeTagNameIndex(elem, curIndex - 1);
                        });
                        $(this).find(':input[type=date]').attr('onblur', 'editVisitDate(\'' + postURL + '\', ' + (curIndex - 1) + ')');
                        $(this).find(':button').attr('onclick', 'deleteVisitDate(\'' + postURL + '\', ' + (curIndex - 1) + ')');
                    } else if (curIndex == index) {
                        $(this).remove();
                    }
                });
            });
}

function postVisitDate(postURL, action, index, vDate) {
    $.post(postURL + '/' + action,
            {
                arrIndex: index,
                visit_date: vDate
            });
}

function appendVisitDate(actionURL, index, today) {
    $('div#visitDates').append(
            '<div class="visit-dates col-sm-4 col-md-3">\n\
            <input name="visitDatesList[' + index + '].id" type="hidden" value="">\n\
            <div class="input-group" style="margin-bottom: 5px;">\n\
                <input type="date" name="visitDatesList[' + index + '].visit_date" \n\
                        class="form-control" onblur="editVisitDate(\'' + actionURL + '\', ' + index + ')" value="' + today + '">\n\
                <span class="input-group-btn">\n\
                    <button class="btn btn-default" onclick="deleteVisitDate(\'' + actionURL + '\', ' + index + ')" type="button">\n\
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>\n\
                    </button>\n\
                </span>\n\
            </div>\n\
                <input name="visitDatesList[' + index + '].order" type="hidden" value="">\n\
        </div>');
}

function changeTagNameIndex(htmlTag, index) {
    var name = $(htmlTag).attr('name');
    var matchRes = name.match(/\d+/);
    $(htmlTag).attr('name', name.substr(0, matchRes.index) + index + name.substr(matchRes.index + matchRes[0].length));
}

function postDate(actionURL) {
    $.post(actionURL,
            {
                prepare_date: $('#datePick').val()
            });
}

function postCheckNumber(actionURL) {
    $.post(actionURL,
            {
                check_number: $('#check_number').val()
            });
}
