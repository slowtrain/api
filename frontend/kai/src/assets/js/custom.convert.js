
var PJT = {
  /**
     * Intro & login
     */
  loginInit: function loginInit () {
    this.introAnimationStart()
  },
  introAnimationStart: function introAnimationStart () {
    const el = document.getElementById('sequenceArea')
    if (el != null) {
      el.addEventListener('click', () => {
        PJT.loadImageRandom()
        document.getElementsByClassName('intro-bg')[0].classList.add('on')
      }, false)

      let sequenceStart = void 0
      let position = 250
      const interval = 25
      const diff = 250
      const sequencesize = 2500
      if (sequenceStart == 1) {
        sequenceStart = setInterval(() => {
          document.getElementById('sequenceArea').style.backgroundPosition = `-${position}px 0px`
          if (position < sequencesize) {
            position += diff
          } else {
            position = 250
            clearInterval(sequenceStart)
            document.getElementById('sequenceArea').addEventListener('click', () => {
              PJT.loadImageRandom()
              document.getElementsByClassName('intro-bg')[0].classList.add('on')
            }, false)
          }
        }, interval)
      }
    }
  },
  loadImageRandom: function loadImageRandom () {
    const imageClass = ['type-1']
    $('.image-bg').addClass(imageClass[Math.floor(Math.random() * imageClass.length)]).addClass('on')
    $('.login-wrap').fadeIn(1500, () => {
      $('header, footer').addClass('on')
      $('.login-wrap input#userId').focus()
      PJT.loginKeyCheck()
    })
  },
  loginKeyCheck: function loginKeyCheck () {
    $('.login-wrap input').on('keyup', (e) => {
      if ($('#userId').val() !== '' && $('#passWd').val() !== '') {
        $('.login-wrap .form-wrap, a.login-button').addClass('on')
      } else {
        $('.login-wrap .form-wrap, a.login-button').removeClass('on')
      }
    })
  },
  scrollPlugIn: function scrollPlugIn () {},
  utilsAliveLinks: function utilsAliveLinks () {
    // header > monitoring link
    const header = document.querySelector('header')
    const headerSelect = header.querySelectorAll('a.header-monitor, header .product-btn')

    headerSelect.forEach((obj) => {
      obj.addEventListener('mouseover', () => {
        document.querySelector('header .product-btn').classList.add('on')
      }, false)
      obj.addEventListener('mouseout', () => {
        document.querySelector('header .product-btn').classList.remove('on')
      }, false)
    })

    // header > profile show hide
    const personalSelect = header.querySelectorAll('a.profile-show-hide, div.personal-list-wrap')
    personalSelect.forEach((obj) => {
      obj.addEventListener('mouseover', () => {
        // document.querySelector('div.personal-list-wrap').classList.add('on');
        document.querySelector('div.personal-list-wrap').style.display = 'block'
      }, false)
      obj.addEventListener('mouseout', () => {
        // document.querySelector('div.personal-list-wrap').classList.remove('on');
        document.querySelector('div.personal-list-wrap').style.display = 'none'
      }, false)
    })

    // header > notice show hide
    const noticeSelect = header.querySelectorAll('a.notice-show-hide, div.notice-list-wrap')
    noticeSelect.forEach((obj) => {
      obj.addEventListener('mouseover', () => {
        // document.querySelector('div.notice-list-wrap').classList.add('on');
        document.querySelector('div.notice-list-wrap').style.display = 'block'
      }, false)
      obj.addEventListener('mouseout', () => {
        // document.querySelector('div.notice-list-wrap').classList.remove('on');
        document.querySelector('div.notice-list-wrap').style.display = 'none'
      }, false)
    })

    // lnb > calendar click
    const calendarLink = document.querySelector('a.lnb-calendar')
    calendarLink.addEventListener('click', () => {
      document.querySelector('.lnb-tools-wrap.calendar').style.display = 'block'
    })

    // lnb > create a report
    const reportLink = document.querySelector('a.lnb-createreport')
    reportLink.addEventListener('click', () => {
      document.querySelector('.lnb-tools-wrap.create-report').style.display = 'block'
      document.querySelector('input.create-report-email').addEventListener('keyup', (e) => {
        if (e.target.value !== '') {
          document.querySelector('div.lnb-tools-wrap.create-report a.b').style.display = 'none'
          document.querySelector('div.lnb-tools-wrap.create-report a.c').style.display = 'inline-block'
        } else {
          document.querySelector('div.lnb-tools-wrap.create-report a.b').style.display = 'inline-block'
          document.querySelector('div.lnb-tools-wrap.create-report a.c').style.display = 'none'
        }
      })
    })

    // lnb > add charts click
    const addchartLink = document.querySelector('a.lnb-addcharts')
    addchartLink.addEventListener('click', () => {
      document.querySelector('.lnb-tools-wrap.add-charts').style.display = 'block'
    })

    // lnb > navigation tooltip
    // const lnbTooltip = document.querySelectorAll('ul.lnb-nav li');
    // lnbTooltip.forEach(function(obj) {
    //     obj.addEventListener('mouseover', function() {
    //         let thisName = obj.dataset.name;
    //         let toolTipDiv = '<div class="tiny-tooltip" style="display: none;">';
    //         toolTipDiv += '<div class="tooltip-container">';
    //         toolTipDiv += '<div class="tooltip-text animated">';
    //         toolTipDiv += thisName;
    //         toolTipDiv += '</div>';
    //         toolTipDiv += '<div class="tooltip-tip animated"></div>';
    //         toolTipDiv += '</div>';
    //         toolTipDiv += '</div>';

    //         setTimeout(function() {
    //             const objLink = obj.children;
    //             $(objLink).append(toolTipDiv);
    //             document.querySelector('.tiny-tooltip').style.display = 'block';
    //         }, 100);
    //     }, false);
    // });
    $('ul.lnb-nav li').on('mouseenter', function () {
      const thisName = $(this).data('name')
      let toolTipDiv = '<div class="tiny-tooltip" style="display: none;">'
      toolTipDiv += '<div class="tooltip-container">'
      toolTipDiv += '<div class="tooltip-text animated">'
      toolTipDiv += thisName
      toolTipDiv += '</div>'
      toolTipDiv += '<div class="tooltip-tip animated"></div>'
      toolTipDiv += '</div>'
      toolTipDiv += '</div>'
      $(this).append(toolTipDiv).delay(500).queue((next) => {
        $('.tiny-tooltip').fadeIn('fast')
        next()
      })
    })
    $('ul.lnb-nav li').on('mouseleave', () => {
      $('div.tiny-tooltip').remove()
    })

    // document click
    document.addEventListener('click', () => {
      const haveToClose = document.querySelectorAll('.lnb-content_popup')
      haveToClose.forEach((obj) => {
        obj.style.display = 'none'
      })
    })

    const lnbKeepContent = document.querySelectorAll('.lnb-wrap li, .lnb-content_popup')
    lnbKeepContent.forEach((obj) => {
      obj.addEventListener('click', (e) => {
        e.stopPropagation()
      })
    })
  },

  /**
     * global
     */
  siteResizeResponse: function siteResizeResponse () {
    const winWidth = window.innerWidth
    const winHeight = window.innerHeight
    document.getElementsByClassName('content-wrap')[0].style.width = `${winWidth - 375}px`
    document.getElementsByClassName('device-list')[0].style.height = `${winHeight - 169}px`
    window.addEventListener('resize', () => {
      const winWidth = window.innerWidth
      const winHeight = window.innerHeight
      const realSize = winWidth - 375
      document.getElementsByClassName('content-wrap')[0].style.width = `${realSize}px`
      document.getElementsByClassName('device-list')[0].style.height = `${winHeight - 169}px`
    }, false)
  }

  // bootstrap modal events
}; $('#modalTempSearch').on('shown.bs.modal', (e) => {
  $('#deviceSearchInput').val('').focus().css('opacity', 0.5)
  $('.search-header input').on('keydown', function () {
    $(this).css('opacity', 1)
  })
})

// after loaded execute
window.onload = function () {
  PJT.loginInit()
  PJT.siteResizeResponse()
  PJT.utilsAliveLinks()
}
