<template>
  <el-tree
    ref="tree"
    v-if="tree.showTreeFlag"
    class="tree"
    node-key="id"
    v-bind:data="tree.data"
    v-bind:default-expanded-keys="tree.expandKeys"
    v-bind:expand-on-click-node="false"
    v-bind:highlight-current="true"
    v-on:node-click="clickNode"
  >
    <span class="tree-text" slot-scope="{node, data}">
      <span v-on:click="clickNode(node)">
        <i v-if="tree.showIconFlag" class="class-svg" v-html="tree.showNodeIconFn(data.data)"></i>
        <span>{{node.label}}</span>
        <span v-if="tree.showCntFlag">{{ nodeChildCnt(data.data) }}</span>
      </span>
      <img v-if="tree.shwoAddIconFn(data.data)" class="tree-add-img" v-on:click="clickAddBtn(data.data)" v-bind:src="require('../../assets/img/icons/icon_set_black/plus@2x.png')">
    </span>
  </el-tree>
</template>
<script>
export default {
  props: ['tree'],
  data () {
    return {
      eventSkipFlag: false
    }
  },
  methods: {
    initTree: function () {
      this.expandKeys = ['CLASSSTRUCTURE']
    },
    nodeChildCnt: function (data) {
      return data.cnt === 0 ? '' : '(' + data.cnt + ')'
    },
    clickNode: function (data) {
      // if (!this.eventSkipFlag) {
      if (!this.eventSkipFlag && !this.$util.isNull(data.$treeNodeId)) {
        this.$emit('clickNode', data)
      }
      this.eventSkipFlag = false
    },
    clickAddBtn: function (data) {
      this.eventSkipFlag = true
      this.$emit('clickAddBtn', data)
    },
    clear: function () {
      this.tree.data = []
      this.refs.tree.$destroy()
      this.refs.tree = null
      console.log(this.tree.data)
    }
  },
  watch: {
    'tree.data': function () {
      if (this.tree.data.length > 0) {
        this.initTree()
      }
    }
  },
  created: function () {},
  mounted: function () {
    if (this.tree.data.length > 0) {
      this.initTree()
    }
  }
}
</script>
