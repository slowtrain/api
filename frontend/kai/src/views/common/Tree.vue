<template>
  <tree
    ref="tree"
    v-if="tree.showTreeFlag"
    v-bind:data="tree.data"
    v-on:node:clicked="clickedAllNode"
    :style="!$util.isNull(tree.style)?tree.style:''"
  >
    <span class="tree-text" slot-scope="{ node }">
      <span v-on:click="clickNode(node)">
        <img v-if="tree.iconImgType === 'img'" class="tree-icon-img" v-bind:src="require('../../assets/img/icons/episode/' + ($util.isNull(tree.showNodeIconFn(node)) ? 'star@2x.png' : tree.showNodeIconFn(node)))"/>
        <i v-else-if="tree.iconImgType === 'svg'" class="class-svg" v-html="tree.showNodeIconFn(node)"></i>
        <i v-else class="tree-icon-img" v-bind:class="tree.showNodeIconFn(node)"></i>
        <span>{{ node.text }}</span>
        <span v-if="tree.isUseCntFlag">{{ node.data.cnt === 0 ? "" : "(" + node.data.cnt + ")" }}</span>
      </span>
      <img v-if="tree.shwoAddIconFn(node)" class="tree-add-img" v-on:click="clickAddBtn(node)" v-bind:src="require('../../assets/img/icons/icon_set_black/plus@2x.png')">
    </span>
  </tree>
</template>
<script>
export default {
  props: ['tree'],
  data () {
    return {
      processFlag: false,
      eventSkipFlag: false
    }
  },
  methods: {
    initTree: function () {
      this.processFlag = true
      this.tree.data = []
      this.tree.showTreeFlag = false
      this.tree.showTreeFlag = true
      this.tree.showTreeFlag = false
      if (this.tree.dataType === 'RAW') {
        this.tree.data = this.makeTreeData(this.tree.rawData, this.tree.rootVal, this.tree.parentColNm, this.tree.childColNm, this.tree.textColNm)
      }
      if (this.tree.isRootDataFlag) {
        this.tree.data = this.makeRootTreeData(this.tree.rootData, this.tree.data)
      }
      setTimeout(() => {
        this.tree.showTreeFlag = true
        setTimeout(() => {
          let id = this.tree.selectData.id
          if (this.$util.isNull(id)) {
            let nodes = this.$refs.tree.find((this.$util.isNull(this.tree.expand) ? {depth: 0} : this.tree.expand))
            if (this.$util.isNull(nodes)) return
            this.expandNodes(nodes[0])
            nodes.select()
            this.tree.selectData = nodes[0].data
          } else {
            let nodes = this.$refs.tree.findAll({data: {id: id}})
            if (this.$util.isNull(nodes)) {
              nodes = this.$refs.tree.find((this.$util.isNull(this.tree.expand) ? {depth: 0} : this.tree.expand))
            }
            this.expandNodes(nodes[0])
            nodes.select()
            this.tree.selectData = nodes[0].data
          }
          this.processFlag = false
        }, 100)
      }, 100)
    },
    makeTreeData: function (dataList, rootVal, parentColNm, childColNm, textColNm) {
      let rtnList = []
      for (let idx in dataList) {
        let data = dataList[idx]
        let dataVal = data[childColNm]
        if (!this.$util.isNull(textColNm)) {
          dataVal = data[textColNm]
        }
        let parentDataVal = data[parentColNm]
        let tempData = {}
        if (rootVal === parentDataVal) {
          tempData.id = data.id
          tempData.text = dataVal
          tempData.data = data
          tempData.children = this.makeTreeData(dataList, data[childColNm], parentColNm, childColNm, textColNm)
          tempData.data.cnt = tempData.children.length
          for (let cntIdx in tempData.children) {
            tempData.data.cnt += tempData.children[cntIdx].data.cnt
          }
          rtnList.push(tempData)
        }
      }
      return rtnList
    },
    makeRootTreeData: function (rootData, data) {
      let rtnData = rootData
      rtnData.children = data
      rtnData.data.cnt = 0
      for (let cntIdx in rtnData.children) {
        rtnData.data.cnt += rtnData.children[cntIdx].data.cnt + 1
      }
      return rtnData
    },
    expandNodes: function (nodes) {
      if (!this.$util.isNull(nodes)) {
        nodes.expand()
        this.expandNodes(nodes.parent)
      }
    },
    clickedAllNode: function (node) {
      node.toggleCollapse()
      if (!this.eventSkipFlag) {
        this.clickNode(node)
      }
      this.eventSkipFlag = false
    },
    clickNode: function (node) {
      this.tree.selectData = node.data
      this.$emit('clickNode', node)
    },
    clickAddBtn: function (node) {
      this.eventSkipFlag = true
      this.tree.selectData = node.data
      this.$emit('clickAddBtn', node)
    }
  },
  watch: {
    'tree.rawData': function (val) {
      if (!this.processFlag) {
        this.initTree()
      }
    }
  },
  created: function () {},
  mounted: function () {
    if (this.tree.rawData.length > 0) {
      if (!this.processFlag) {
        this.initTree()
      }
    }
  },
  beforeDestroy: function () {
    this.tree.data = []
    this.tree.rawData = []
    this.$nextTick(function () {
      this.tree.showTreeFlag = false
    })
  }
}
</script>
