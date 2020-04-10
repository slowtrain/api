import $ from '../../../node_modules/jquery/dist/jquery.min'

/* eslint-disable */
let HEADER = {
  headerMonitoryShow: function() {
    $('header .product-btn').addClass('on');
  },
  headerMonitoryHide: function() {
    $('header .product-btn').removeClass('on');
  },
  headerProfileShow: function() {
    $('div.personal-list-wrap').stop().fadeIn('fast');
  },
  headerProfileHide: function() {
    $('div.personal-list-wrap').stop().fadeOut('fast');
  },
  headerNoticeShow: function() {
    $('div.notice-list-wrap').stop().fadeIn(300);
  },
  headerNoticeHide: function() {
    $('div.notice-list-wrap').stop().fadeOut(300);
  },
}

export default HEADER
