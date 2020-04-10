import $ from '../../../node_modules/jquery/dist/jquery.min'

/* eslint-disable */
var GLOBAL = {
    state: {
        filter: false
    },

    /*
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
  */
   
  // 브라우져 리사이즈 시 영역확보 및 조정
  siteResizeResponse: function siteResizeResponse() {
    var winWidth = $(window).width();
    var winHeight = $(window).height();
    $('.content-wrap').css('width', winWidth - 375);
    $('.device-list').css('height', winHeight - 169);
    $('div.full-popup-wrap div.inner h2').css('width', winWidth - 40);

    $(window).on('resize', function () {
      var winWidth = $(window).width();
      var winHeight = $(window).height();
      var realSize = winWidth - 375;
      $('.content-wrap').css('width', realSize);
      $('div.full-popup-wrap div.inner h2').css('width', winWidth - 40);

      if (!GLOBAL.state.filter) {
        // is filter open
        $('.device-list').css('height', winHeight - 169);
      } else {
        $('.device-list').css('height', winHeight - 290);
      }
    });
  }
};

export default GLOBAL
