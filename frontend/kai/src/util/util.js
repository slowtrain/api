import Vue from 'vue'
import axios from 'axios'
import router from '../router/index'
import store from '../store/store'
import {parse} from 'json2csv'
import {eventBus} from '../eventBus/eventBus'

Vue.use(axios)
Vue.use(router)

export default {
  workflow: function (vue, objectId, uid, callbackFunc) {
    axios.get(
      '/episode/api/system/common/workflow/wfinstance', {params: {objectId: objectId, uid: uid}}
    ).then(res => {
      if (res.data.currentWFInstance === null) {
        eventBus.$emit('wfprocessOpen', {objectId: objectId, uid: uid}, callbackFunc)
      } else {
        eventBus.$emit('wfoptionOpen', {objectId: objectId, uid: uid}, callbackFunc)
      }
    })
  },
  wfprogress: function (vue, objectId, uid, wfprocessUid, connectorUid, callbackFunc) {
    vue.$emit('wfoptionClose')
    vue.$emit('wfprocessClose')
    axios.post(
      '/episode/api/system/common/workflow/wfprogress', {objectId: objectId, uid: uid, wfprocessUid: wfprocessUid, connectorUid: connectorUid}
    ).then(res => {
      if (res.data.isOption) {
        eventBus.$emit('wfoptionOpen', {objectId: objectId, uid: uid}, callbackFunc)
      } else {
        callbackFunc(uid)
        this.esmessage(vue, 'check', '워크플로우가 진행되었습니다.')
      }
    })
  },
  esmessage: function (vue, type, msg) {
    vue.$root.$children[0].esmessage(type, msg)
  },
  esconfirm: function (vue, type, msg, complete, cancle) {
    vue.$root.$children[0].esconfirm(type, msg, complete, cancle)
  },
  date: function (date) {
    let dateValue = null
    if (!this.isNull(date)) {
      const dateType = date.substring(0, date.indexOf('.'))
      dateValue = new Date(dateType)
      dateValue = dateValue.toISOString().split('T')[0]
    }
    return dateValue
  },
  dateTime: function (date) {
    let dateValue = null
    let lang = 'ko-kr'
    if (!this.isNull(store.getters.getUser)) {
      lang = store.getters.getUser.lang
    }
    if (!this.isNull(date)) {
      const dateType = date.substring(0, date.indexOf('.'))
      dateValue = new Date(dateType).toLocaleString(lang)
    }
    return dateValue
  },
  dateTimeWithTimeZone: function (date) {
    let dateValue = null
    if (!this.isNull(date)) {
      const dateType = date.substring(0, date.indexOf('.'))
      let dt = new Date(dateType)
      dt = new Date(Date.UTC(dt.getFullYear(), dt.getMonth(), dt.getDate(), dt.getHours(), dt.getMinutes(), dt.getSeconds()))
      dateValue = dt.toLocaleString(store.getters.getUser.lang)
    }
    return dateValue
  },
  dateTimeCalendar: function (date) {
    let dateValue = null
    if (typeof date !== 'string') {
      return date
    }
    if (!this.isNull(date)) {
      const dateType = date.substring(0, date.indexOf('.'))
      let dt = new Date(dateType)
      dateValue = new Date(Date.UTC(dt.getFullYear(), dt.getMonth(), dt.getDate(), dt.getHours(), dt.getMinutes(), dt.getSeconds()))
    }
    return dateValue
  },
  isNull: function (val) {
    if (val === null || val === '' || val === undefined || val === 'undefined') {
      return true
    }
    return false
  },
  lengthChk: function (length, min, max) {
    if (length < min || length > max) {
      return true
    }
    return false
  },
  setLabels: function (appId, labels, titles) {
    let user = store.getters.getUser
    let lang = (user === null) ? 'ko-kr' : user.lang
    return axios.get(
      '/episode/api/system/label?appId=' + appId + '&lang=' + lang
    ).then(response => {
      let data = response.data

      for (let i = 0; i < data.length; i++) {
        labels[data[i].name] = data[i].description
      }
      if (titles !== undefined) {
        for (let i = 0; i < titles.length; i++) {
          titles[i].label = labels[titles[i].prop]
        }
      }
    }).catch((error) => {
      console.log(error)
    })
  },
  csvExport: function (data, fields, fieldNames, fileName) {
    let date = new Date(new Date().setHours(new Date().getHours() + 9))
    let dateStr = date.toISOString().split('.')[0].replace(/[^0-9]/gi, '')
    fileName = fileName + '_' + dateStr
    const result = parse(data, {fields})
    // chrome
    if (!window.navigator.msSaveBlob) {
      let blob = new Blob(['\uFEFF' + result], {
        type: 'data:text/csv;charset=UTF-8'
      })
      let link = document.createElement('a')
      let csvUrl = URL.createObjectURL(blob)
      link.href = csvUrl
      link.style = 'visibility;hidden'
      link.download = `${(fileName || 'file')}.csv`
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link)
      // let csvContent = 'data:text/csv;charset=UTF-8,\uFEFF' + result
      // let encodedUri = encodeURI(csvContent)
      // let link = document.createElement('a')
      // link.setAttribute('href', encodedUri)
      // link.setAttribute('download', `${(fileName || 'file')}.csv`)
      // document.body.appendChild(link)
      // link.click()
      // document.body.removeChild(link)
    // explorer
    } else {
      window.navigator.msSaveOrOpenBlob(new Blob(['\uFEFF' + result], {type: 'data:text/csv;charset=UTF-8'}), `${fileName}.csv`)
    }
  },
  initEvent: function (obj, paramList) {
    paramList.forEach(param => {
      eventBus.$off(param.name)
      eventBus.$on(param.name, param.function)
    })
  },
  readAuth: function (vue, vueName) {
    let authorities = vue.$store.getters.getAuth
    let auth = authorities.filter(r => r.authorityId === vueName)
    if (auth.length !== 0) {
      return true
    } else {
      return false
    }
  },
  getComboData: function (param) {
    param.paramList.forEach(paramObj => {
      let query = paramObj.query
      if (this.isNull(query)) {
        query = '/episode/api/system/common/domain?domainId=' + paramObj.domainId
      }
      axios.get(query).then(res => {
        if (!this.isNull(paramObj.sel)) {
          if (paramObj.sel === 'S') {
            res.data.unshift({key: '', value: 'Select'})
          } else if (paramObj.sel === 'A') {
            res.data.unshift({key: '', value: 'All'})
          }
        }
        let parentDataObj = param.obj.$data
        let tempList = paramObj.dataObj.split('.')
        tempList.forEach((data, idx) => {
          if (tempList.length === idx + 1) {
            parentDataObj[data] = res.data
          }
          parentDataObj = parentDataObj[data]
        })
      }).catch(error => {
        console.log(error)
      })
    })
  },
  onlyNumber: function (e) {
    let passKey = ['ArrowLeft', 'ArrowRight', 'Left', 'Right', 'Backspace', 'Delete', 'Del']
    if (this.isNull(e.key.replace(/[^0-9]/g, '')) && !(passKey.indexOf(e.key) > -1)) {
      e.preventDefault()
    }
  },
  perfTimestampDate: function (date) {
    let year = date.getFullYear()
    let month = date.getMonth() + 1
    let day = date.getDate()
    let hours = date.getHours()
    let minutes = date.getMinutes()
    if (month < 10) month = '0' + month
    if (day < 10) day = '0' + day
    if (hours < 10) hours = '0' + hours
    if (minutes < 10) minutes = '0' + minutes
    minutes = String(minutes)
    if (parseInt(minutes.substr(1, 1)) < 5) {
      minutes = minutes.substr(0, 1) + '0'
    } else {
      minutes = minutes.substr(0, 1) + '5'
    }
    return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':00'
  },
  timestampDate: function (date) {
    let year = date.getFullYear()
    let month = date.getMonth() + 1
    let day = date.getDate()
    let hours = date.getHours()
    let minutes = date.getMinutes()
    let seconds = date.getSeconds()
    if (month < 10) month = '0' + month
    if (day < 10) day = '0' + day
    if (hours < 10) hours = '0' + hours
    if (minutes < 10) minutes = '0' + minutes
    if (seconds < 10) seconds = '0' + seconds
    return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds
  },
  getDateType: function (date) {
    let year = date.getFullYear()
    let month = date.getMonth() + 1
    let day = date.getDate()
    if (month < 10) month = '0' + month
    if (day < 10) day = '0' + day
    return year + '/' + month + '/' + day + ' 00:00:00'
  },
  singleChartPush: function (chartData, chartOption, data, chart) {
    let previousDay = data.previousDay
    let day = data.day
    let rtOld = chartData.rt
    if (rtOld !== day.rt) {
      if (!this.isNull(day.originDt) && !this.isNull(previousDay.originDt)) {
        chartData.labels.push(day.originDt)
        chartData.datasets[0].data.push(day.rscval)
        chartData.datasets[1].data.push(previousDay.rscval)
        chartData.rt = day.rt
        chart.update()
      } else if (!this.isNull(day.originDt) && this.isNull(previousDay.originDt)) {
        chartData.labels.push(day.originDt)
        chartData.datasets[0].data.push(day.rscval)
        chartData.datasets[1].data.push(0)
        chartData.rt = day.rt
        chart.update()
      } else if (this.isNull(day.originDt) && !this.isNull(previousDay.originDt)) {
        chartData.labels.push(day.time)
        chartData.datasets[0].data.push(0)
        chartData.datasets[1].data.push(previousDay.rscval)
        chartData.rt = day.rt
        chart.update()
      } else {
        chartData.labels.push(day.time)
        chartData.datasets[0].data.push(0)
        chartData.datasets[1].data.push(0)
        chartData.rt = day.rt
        chart.update()
      }
    }
  },
  multipleChartPush: function (chartData, chartOption, data, chart) {
    let day = data[0]
    let addChk = false
    let rtOld = chartData.rt
    let rtNew = null
    let origin = null
    data.some(r => {
      if (!this.isNull(r.originDt)) {
        addChk = true
        origin = r.originDt
        rtNew = r.rt
      }
      return (!this.isNull(r.originDt))
    })

    if (rtOld !== day.rt) {
      if (addChk) {
        chartData.labels.push(origin)
        chartData.rt = rtNew
        data.forEach((r, i) => {
          if (!this.isNull(r.rscval)) chartData.datasets[i].data.push(r.rscval)
          else chartData.datasets[i].data.push(0)
        })
        chart.update()
      } else {
        chartData.labels.push(day.time)
        chartData.rt = day.rt
        data.forEach((r, i) => {
          chartData.datasets[i].data.push(0)
        })
        chart.update()
      }
    }
  },
  upperCase: function (val) {
    val = val.toUpperCase()
  },
  lowerCase: function (val) {
    val = val.toLowerCase()
  },
  getRgba: function (data, backColor, boardColor) {
    data.forEach(r => {
      let r1 = Math.floor(Math.random() * 255)
      let r2 = Math.floor(Math.random() * 255)
      let r3 = Math.floor(Math.random() * 255)
      backColor.push('rgba(' + r1 + ',' + r2 + ',' + r3 + ', 0.5)')
      boardColor.push('rgba(' + r1 + ',' + r2 + ',' + r3 + ', 0.1)')
    })
  },
  getColor: function (num, backColor) {
    for (let i = 0; i < num; i++) {
      let setColor = '#' + Math.round(Math.random() * 0xffffff).toString(16)
      backColor.push(setColor)
    }
  },
  move: function (vue, vueName, id) {
    eventBus.$emit('dialogClose')
    vue.$root.$router.push('/gate')
    vue.$root.$router.push(
      {
        name: vueName,
        params: {id: id}
      })
  },
  install: function (Vue, options) {
    Vue.prototype.$isNull = function (val) {
      if (val === null || val === '' || val === undefined || val === 'undefined') {
        return true
      }
      return false
    }
    Vue.prototype.$csvExport = function (data, fields, fieldNames, fileName) {
      let date = new Date(new Date().setHours(new Date().getHours() + 9))
      let dateStr = date.toISOString().split('.')[0].replace(/[^0-9]/gi, '')
      fileName = fileName + '_' + dateStr
      const result = parse(data, {fields})
      // chrome
      if (!window.navigator.msSaveBlob) {
        let csvContent = 'data:text/csv;charset=UTF-8,\uFEFF' + result
        let encodedUri = encodeURI(csvContent)
        let link = document.createElement('a')
        link.setAttribute('href', encodedUri)
        link.setAttribute('download', `${(fileName || 'file')}.csv`)
        document.body.appendChild(link)
        link.click()
        document.body.removeChild(link)
      // explorer
      } else {
        window.navigator.msSaveOrOpenBlob(new Blob(['\uFEFF' + result], {type: 'data:text/csv;charset=UTF-8'}), `${fileName}.csv`)
      }
    }
  }
}
