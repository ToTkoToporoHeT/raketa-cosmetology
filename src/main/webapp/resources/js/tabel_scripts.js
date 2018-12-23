/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



$('document').ready(function () {
    $('table input[checked=checked]').parents('tr').toggleClass('checked_row');
    $('.coloring td').click(function (e) {
        var elm = e.target || event.srcElement;
        //if click on checkbox
        if (elm.tagName.toLowerCase() == 'input') {
            return;
        }
        //if click on checkbox cell
        var $flag = $("input[type=checkbox], input[type=radio]", $(this));
        //if click on cell without checkbox
        if ($flag.html() == null) {
            //if this cell has no select
            if ($('select', $(this)).html() == null) {
                $flag = $("input[type=checkbox], input[type=radio]", $(this).parent());
            } else {
                return;
            }
        }
        var checked = $flag.attr('checked');
        if (checked == null) {
            var prevChoice = $('table input[type=radio]:checked');
            prevChoice.removeAttr('checked');
            prevChoice.parents("tr").toggleClass('checked_row');
            $flag.attr("checked", "checked");
            $(this).parent().toggleClass('checked_row');
        } else {
            $flag.removeAttr('checked');
            $(this).parent().toggleClass('checked_row');
        }
    });
});

$(function($){
	$('.table').footable();
});
