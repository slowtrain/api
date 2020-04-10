<!--
 Class Name  : RpaCanvas.vue
 Description : 모니터 대시보드 > 업무별 응답시간 > 캔버스 컴포넌트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.23  이정호  최초 생성
-->
<template>
      <canvas ref='ctx' width="15px" height="15px" />
</template>

<script>
export default {
  name: 'MONITORDASHBOARD',
  props: ['color'],
  data: function () {
    return {
    }
  },
  computed: {
  },
  mounted () {
    let ctx = this.$refs.ctx.getContext('2d')
    ctx.beginPath()
    ctx.fillStyle = this.color
    ctx.arc(7, 10, 5, 0, Math.PI * 2, false)
    ctx.fill()
    ctx.closePath()
  }
}
</script>
