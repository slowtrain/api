import $ from '../../../node_modules/jquery/dist/jquery.min'

/* eslint-disable */
let MAIN = {
  deviceFilterShow: function() {
    var deviceListHeight = $('.device-list').height();
    $('.device-wrap div.filter-options').addClass('on');
    $('.device-list').css('height', deviceListHeight - 121);
    PJT.state.filter = true;
    $('.filter-options > .option a').on('click', function() {
      if($(this).hasClass('clear-all')) {
        $('.filter-options > .option a').removeClass('on');
      }
      else {
        $(this).toggleClass('on');
      }
    });
  },

  deviceAddListShow: function() {
    $('#addDeviceList').fadeIn('fast');
  },

  deviceAddListHide: function() {
    $('#addDeviceList').fadeOut('fast');
  },

  deviceRemoveList: function() {
    $('.device-wrap div.device-inner').addClass('on');
    console.log($('.device-wrap div.device-inner'))
  },

  utilsAliveLinks: function utilsAliveLinks() {
    $('.default_full-popup a.close').on('click', function () {
        $('.default_full-popup').fadeOut('fast');
    });

    // 바탕화면 클릭 시, 열려있는 팝업등에 대한 닫기 처리
    $(document).on('click', function () {
        $('.lnb-content_popup, .select-wrap div.options, .grid_select-option').fadeOut('fast');
        $('.device-list div.device-inner').removeClass('on');
    });
    $('.lnb-wrap li, .lnb-content_popup, .select-wrap, .select-wrap div.options, .device-wrap a.device-remove, .device-wrap a.remove').on('click', function (e) {
        e.stopPropagation();
    });
},


}

export default MAIN
