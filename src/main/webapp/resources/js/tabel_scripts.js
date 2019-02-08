/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



$('document').ready(function () {
    $('table input[checked=checked]').parents('tr').toggleClass('checked_row');
    $('.coloring td').click(function (e) {
        var elm = e.target || event.srcElement;
        //если клик был на input то ничего не делать
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

            //если есть отметки об ошибках убрать их
            var materialInput = $(this).parents('tr').find('input[id^=usMaterialCount]');
            if (materialInput.hasAttr("title")) {
                removeError(materialInput);
            }
        }
    });
});

$(function ($) {
    $('.table').footable();
});

//функции для работы с формой выбора метриалов
{
    function checkCheckedMaterials() {
        var arr = $('table .checked_row input[type=number]');
        var result = true;

        $.each(arr, function (index, value) {
            if (!$(value).val()) {
                $(value).tooltip({
                    title: 'Заполните количество',
                    delay: {show: 20, hide: 400}
                });
                $(value).tooltip('show');
                $(value).attr("style", "border-color: red");
                result = false;
            }
            ;
        });

        return result;
    }

    $(function ($) {
        $('table').on('blur', '[title]', function () {
            if ($(this).parents('tr').hasClass("checked_row") && $(this).val()) {
                removeError($(this));
            }
        });
    });
}

//убирает отметки об ошибках
function removeError($param) {
    $param.tooltip('destroy');
    $param.removeAttr("data-original-title");
    $param.removeAttr("title");
    $param.removeAttr("style");
}

//проверяет наличие атрибута
(function ($) {
    $.fn.hasAttr = function ($attr) {
        console.log($(this).attr($attr));
        if ($(this).attr($attr) === undefined) {
            return false;
        }

        return true;
    };
})(jQuery);