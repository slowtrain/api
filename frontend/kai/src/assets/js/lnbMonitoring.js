import $ from '../../../node_modules/jquery/dist/jquery.min'

/* eslint-disable */
let LnbMonitoring = {
  lnbCalendarShow: function() {
    $('.lnb-tools-wrap.calendar').fadeIn('fast');
  },

  lnbCreateReportShow: function() {
    $('.lnb-tools-wrap.create-report').fadeIn('fast');
  },

  lnbCreateReportEmail: function() {
    if($('#reportEmail').val() !== '') {
      $('div.lnb-tools-wrap.create-report a.b').css('display','none');
      $('div.lnb-tools-wrap.create-report a.c').css('display','inline-block');
    }
    else {
      $('div.lnb-tools-wrap.create-report a.b').css('display','inline-block');
      $('div.lnb-tools-wrap.create-report a.c').css('display','none');
    }
  },

  lnbSnapShot: function() {
    $('.lnb-tools-wrap.snap-shot').fadeIn('fast');
  },

  lnbAddChart: function() {
    $('.lnb-tools-wrap.add-charts').fadeIn('fast');
  },

  lnbMouseEnter: function(obj) {
    var thisName = obj.id;
    var toolTipDiv = '<div class="tiny-tooltip" style="display: none;">';
    toolTipDiv += '<div class="tooltip-container">';
    toolTipDiv += '<div class="tooltip-text animated">';
    toolTipDiv += thisName;
    toolTipDiv += '</div>';
    toolTipDiv += '<div class="tooltip-tip animated"></div>';
    toolTipDiv += '</div>';
    toolTipDiv += '</div>';
    $(obj).append(toolTipDiv).delay(500).queue(function(next){
      $('.tiny-tooltip').fadeIn('fast');
      next();
    });
  },

  lnbMouseLeave: function() {
    $('div.tiny-tooltip').remove();
  }
}

export default LnbMonitoring
