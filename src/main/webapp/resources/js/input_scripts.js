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
    var idStr = $('#visitDates').find('div.visit-dates:last').attr('id');
    var index;
    if (idStr) {
        index = parseInt(idStr.slice(9)) + 1;
        if (index == NaN)
            index = 0;
    } else {
        index = 0;
    }

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
            function () {
                $('div#visitDates').append(
                        '<div id="div_vDate' + index + '" class="form-group visit-dates col-sm-4 col-md-3">\n\
                <input id="visitDatesList' + index + '.id" name="visitDatesList[' + index + '].id" type="hidden" value="">\n\
                <div class="input-group">\n\
                    <input type="date" id="vDate' + index + '" name="visitDatesList[' + index + '].visit_date" \n\
                            class="form-control onblur="postVisitDate(' + postURL + ')" value="' + today + '">\n\
                    <span class="input-group-btn">\n\
                        <button class="btn btn-default" onclick="deleteVisitDate(' + index + ', ' + postURL + ')" type="button">\n\
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>\n\
                        </button>\n\
                    </span>\n\
                </div>\n\
                <input id="visitDatesList' + index + '" name="visitDatesList[' + index + '].order" type="hidden" value="">\n\
             </div>');
            });
}

function editVisitDate(postURL, index) {
    $.post(postURL + '/edit',
            {
                arrIndex: index,
                visit_date: $('#vDate' + index).val()
            });
}

function deleteVisitDate(postURL, index) {
    $.post(postURL + '/delete',
            {
                arrIndex: index,
                visit_date: null
            },
            function () {
                $('#div_vDate' + index).remove();
            });
}

function postVisitDate(postURL, action, index, vDate) {
    $.post(postURL + '/' + action,
            {
                arrIndex: index,
                visit_date: vDate
            });
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
