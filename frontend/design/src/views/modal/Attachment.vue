<!--
 Class Name  : Attachment.vue
 Description : Attachment 컴포넌트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.19  이정호  최초 생성
-->
<template>
  <div>
    <div class='row'>
      <div class='col-md-4'>
        <div class="search-component sm">
          <input v-if="$util.isNull(showInputFlag)?true:showInputFlag" class="formdata sm" style="width: 100%;" v-model="attachmentGrid.filters[0].value" :placeholder=labels.search />
        </div>
      </div>
    </div>
    <div class='row'>
      <div class='col'>
        <el-form label-width="120px">
          <div class='row mt-2'>
            <div class='col-md-12'>
              <p class='grid-line'></p>
              <data-tables ref="orgTable" layout='table, pagination' :data='attachmentGrid.data' :filters='attachmentGrid.filters'
               :page-size='attachmentGrid.pageProps.pageSize' :pagination-props='attachmentGrid.pageProps'>
                <el-table-column width="100" prop="id" :label="labels.fileNo" ></el-table-column>
                <el-table-column width="550" prop="filename" :label="labels.fileName" ></el-table-column>
                <el-table-column prop="description" :label="labels.fileDescription"></el-table-column>
                <el-table-column align="right" width="100" >
                  <template slot-scope="props">
                    <el-button  v-on:click="fileDownload(props.row.id)" :title="labels.fileDownload" style="padding: 0; width:30px; height: 30px;">
                      <img src="../../assets/img/icons/icon_search_download.png" width="60%" />
                    </el-button>
                    <el-button  v-on:click="delFile(props.row.id)" :title="labels.fileDelete" style="padding: 0; width:30px; height: 30px;">
                      <img src="../../assets/img/icons/icon_delete.png" width="60%" />
                    </el-button>
                   </template>
                 </el-table-column>
              </data-tables>
             </div>
           </div>
       </el-form>
       </div>
     </div>
  </div>
</template>

<script>
import {eventBus} from '@/eventBus/eventBus'
export default {
  name: 'ATTACHMENT',

  data () {
    return {
      labels: {
        title: '',
        fileName: '',
        fileDescription: '',
        fileDownload: '',
        fileDelete: '',
        fileNo: '',
        fileError: '',
        warnMessage: '',
        // ownerTable: '',
        // owerId: '',
        colse: '',
        search: '',
        deleteMessage: '',
        confirmDelete: ''
      },
      attachmentGrid: {
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return ' font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          },
          cekkStyle: ''
        },
        pageProps: {
          pageSize: 5,
          pageSizes: [5, 10, 15]
        },
        rowIndex: -1,
        data: [],
        filters: [{
          prop: ['description'],
          value: ''
        }],
        filteredData: [],
        titles: [
          { prop: 'id', label: 'id', width: '100px' },
          { prop: 'filename', label: 'filename', width: '100px' },
          { prop: 'description', label: 'description', width: '100px' },
          { prop: 'attachmentId', label: 'attachmentId', width: '100px' },
          { prop: 'ownertable', label: 'ownertable', width: '100px' },
          { prop: 'ownerid', label: 'ownerid', width: '100px' },
          { prop: 'urlname', label: 'urlname', width: '100px' }
        ],
        columns: ['id', 'filename', 'description', 'attachmentId', 'ownertable', 'ownerid', 'urlname'],
        columnNames: ['id', 'filename', 'description', 'attachmentId', 'ownertable', 'ownerid', 'urlname'],
        currentData: {
        }
      }
    }
  },

  props: {
    ownertable: {
      type: String
    },
    ownerid: {
      type: Number
    },
    showInputFlag: {
      type: Number
    }
  },
  watch: {
    'ownertable': function (val) {
      this.getFileList()
    },
    'ownerid': function (val) {
      this.getFileList()
    }
  },
  methods: {
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      if (!this.$util.isNull(this.ownerid)) {
        this.getFileList()
      }
    },
    fileDownload: function (id) {
      this.$http.get('/episode/api/common/attachment/' + id + '/download', {responseType: 'blob'}
      ).then(res => {
        if (!window.navigator.msSaveBlob) {
          const url = window.URL.createObjectURL(new Blob([res.data]))
          const link = document.createElement('a')
          link.href = url
          const filename = decodeURI(res.headers.filename)
          link.setAttribute('download', filename)
          document.body.appendChild(link)
          link.click()
        } else {
          const filename = res.headers.filename
          window.navigator.msSaveOrOpenBlob(new Blob([res.data]), filename)
        }
      }).catch(error => {
        this.$util.esmessage(this, 'warn', this.labels.fileError)
        console.log(error)
      })
    },

    delFile: function (id) {
      let $this = this
      this.$util.esconfirm(this, 'delete', this.labels.confirmDelete, function () {
        this.$http.delete(
          '/episode/api/common/attachment/' + id
        ).then(res => {
          $this.getFileList()
          this.$util.esmessage(this, 'delete', $this.labels.deleteMessage)
        }).catch(error => {
          this.$util.esmessage(this, 'warn', $this.labels.warnMessage)
          console.log(error)
        })
      })
    },
    getFileList: function () {
      this.$http.get('/episode/api/common/attachment', {params: {'ownertable': this.ownertable, 'ownerid': this.ownerid}}
      ).then(res => {
        this.attachmentGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    fileListAdd: function (attachment) {
      attachment.forEach(row => {
        this.attachmentGrid.data.unshift(row)
      })
    }
  },
  mounted () {
    let $this = this
    eventBus.$off('fileListAdd')
    eventBus.$on('fileListAdd', function (attachment) {
      $this.fileListAdd(attachment)
    })
  },

  created () {
    this.initialize()
  }
}
</script>
