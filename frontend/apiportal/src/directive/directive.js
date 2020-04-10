export default {
  install: function (Vue) {
    Vue.directive('upper', {
      inserted: function (el) {
        el.addEventListener('keydown', function (e) {
          e.target.value = e.target.value.toUpperCase()
        })
        el.addEventListener('keyup', function (e) {
          e.target.value = e.target.value.toUpperCase()
        })
        el.addEventListener('change', function (e) {
          e.target.value = e.target.value.toUpperCase()
        })
      }
    })
    Vue.directive('lower', {
      inserted: function (el) {
        el.addEventListener('keydown', function (e) {
          e.target.value = e.target.value.toLowerCase()
        })
        el.addEventListener('keyup', function (e) {
          e.target.value = e.target.value.toLowerCase()
        })
        el.addEventListener('change', function (e) {
          e.target.value = e.target.value.toLowerCase()
        })
      }
    })
    Vue.directive('number', {
      inserted: function (el) {
        el.addEventListener('keydown', function (e) {
          if (e.key === 'Backspace') return
          if (Vue.prototype.$isNull(e.key.replace(/[^0-9]/g, ''))) {
            e.preventDefault()
          }
        })
        el.addEventListener('change', function (e) {
          el.value = e.target.value.replace(/[^0-9]/g, '')
        })
      }
    })
  }
}
