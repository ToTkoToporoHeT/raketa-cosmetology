/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function addField() {
    var idStr = $('#telephone_numbers').find('div.tel-number:last').attr('id');
    var telnum;
    if (idStr) {
        telnum = parseInt(idStr.slice(10)) + 1;
        if (telnum == NaN) telnum = 0;
    }
    else {
        telnum = 0;
    }
    $('div#telephone_numbers').append(
            '<div id="tel_number' + telnum + '" class="form-group tel-number">\n\
                <input id="telephonenumbersList' + telnum + '.id" name="telephonenumbersList[' + telnum + '].id" type="hidden" value="">\n\
                <div class="input-group">\n\
                    <input id="tel' + telnum + '" name="telephonenumbersList[' + telnum + '].telephoneNumber" class="form-control" type="text" value="">\n\
                    <span class="input-group-btn">\n\
                        <button class="btn btn-default" onclick="deleteField(' + telnum + ')" type="button">Del</button>\n\
                    </span>\n\
                </div>\n\
                <input id="telephonenumbersList' + telnum + '.customer" name="telephonenumbersList[' + telnum + '].customer" type="hidden" value="">\n\
             </div>');
}

function deleteField(id) {
    $('div#tel_number' + id).remove();
}
/*<input id="telephonenumbersList0.id" name="telephonenumbersList[0].id" type="hidden" value="">
 <input id="tel0" name="telephonenumbersList[0].telephoneNumber" class="form-control" type="text" value="">
 <input id="telephonenumbersList0.customer" name="telephonenumbersList[0].customer" type="hidden" value="">*/