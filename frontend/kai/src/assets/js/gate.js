import $ from '../../../node_modules/jquery/dist/jquery.min'

/* eslint-disable */
let GATE = {
  introAnimationStart: function() {
    let sequenceStart;
    let position = 250;
    const interval = 25;
    const diff = 250;
    const sequencesize = 2500;
    if(sequenceStart == 1) {
      sequenceStart = setInterval(() => {
        document.getElementById('sequenceArea').style.backgroundPosition = `-${position}px 0px`;
        if(position < sequencesize) {
          position = position + diff;
        }
        else {
          position = 250;
          clearInterval(sequenceStart);
        }
      }, interval);
    }
  },

  skipIntroAnimation: function() {
    GATE.loadImageRandom();
    $('.intro-bg').fadeOut('slow');
  },

  loadImageRandom: function() {
    var imageClass = ['type-1'];
    $('.image-bg').addClass(imageClass[Math.floor(Math.random() * imageClass.length)]).addClass('on');
    $('.login-wrap').fadeIn(1500, function() {
      $('.login-header, .login-footer').addClass('on');
      $('.login-wrap input#username').focus();
      GATE.loginKeyCheck();
    });
  },

  loginKeyCheck: function() {
    if($('#username').val() !== '' && $('#password').val() !== '') {
      $('.login-wrap .form-wrap, a.login-button').addClass('on');
    }
    else {
      $('.login-wrap .form-wrap, a.login-button').removeClass('on');
    }
  },

  scrollPlugIn: function() {

  },

  loginButtonSubmit: function() {
    $('div.gate-wrapper').fadeOut('fast');
    return false;
  }
};

export default GATE
