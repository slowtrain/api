<!--
 Class Name  : FileUpload.vue
 Description : 파일업로드 팝업
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.19  이정호  최초 생성
-->
<template>
  <el-dialog :title="labels.title" :visible="true" width="500px" :show-close="false">
    <div class='row'>
      <div class='col'>
        <el-form label-width="120px">
          <div class="form-row mt-2">
            <div class='col-md-12' style="padding-right:1%">
              <label for="description">{{labels.description}}</label>
                <div class="row">
                  <div class='col-md-9'>
                    <div class="search-component sm">
                      <input id="description" class="formdata sm float-right h" style="width: 100%" v-model="uploadDescription" v-bind:maxlength="50"/>
                    </div>
                  </div>
                  <div class='col-md-3' >
                    <div class='d-flex justify-content-end'>
                      <div class="input-fileup-container sm">
                       <input class="input-file" id="files" type="file" ref="files" multiple v-on:change="filesUpload">
                       <label tabindex="0" for="files" class="input-file-trigger sm">{{labels.search}}</label>
                      </div>
                    </div>
                  </div>

                </div>
              </div>
            <div class='col-md-12' style="padding-right:1%">
              <div class="row" style="padding-top:10px">
                <div class='col-md-3'>
                  <div class="search-component sm">
                    <label for="files">{{labels.attachedFile}}</label>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class='row mt-2'>
            <div class='col-md-12'>
              <p class='grid-line'></p>
              <data-tables layout='table' :data='fileGrid.data' >
                <el-table-column prop="fileName" :label="labels.fileName"></el-table-column>
                <el-table-column prop="fileSize" :label="labels.fileSize" width="150"></el-table-column>
                <el-table-column align="right" width="60">
                  <template slot-scope="props">
                    <el-button  v-on:click="delFile(props.$index)" :title="labels.delete" style="padding: 0; width:30px; height: 30px;">
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
    <span slot="footer" class="dialog-footer">
      <b-button variant="primary" v-on:click="submitFiles()"  class="px-3">{{labels.save}}</b-button>
      <b-button variant="primary" v-on:click="popupClose"  class="px-3">{{labels.close}}</b-button>
    </span>
  </el-dialog>
</template>

<script>
import {eventBus} from '@/eventBus/eventBus'
export default {
  name: 'FILEUPLOAD',

  data () {
    return {
      uploadDescription: '',
      files: [],
      extentions: [],
      labels: {
        title: '',
        saveMessage: '',
        save: '',
        fileName: '',
        fileSize: '',
        search: '',
        delete: '',
        close: '',
        attachedFile: '',
        description: '',
        failMessage: '',
        descriptionMessage: '',
        fileMessage: '',
        fileSizeMessage: '',
        contentTypeMessage: ''
      },
      fileGrid: {
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return ' font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px width:400px'
          },
          cekkStyle: ''
        },
        rowIndex: -1,
        data: [],
        filteredData: [],
        titles: [
          { prop: 'id', label: 'id', width: '100px' },
          { prop: 'fileName', label: 'fileName', width: '100px' },
          { prop: 'fileSize', label: 'fileSize', width: '100px' }
        ],
        columns: ['fileName', 'fileSize'],
        columnNames: ['fileName', 'fileSize'],
        currentData: {
          id: '',
          fileName: '',
          fileSize: ''
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
    }
  },

  methods: {
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      this.$http.get('/episode/api/common/attachment/extentions'
      ).then(res => {
        this.extentions = res.data.toUpperCase().split(',')
        // this.extentions = res.data.replace(/,/gi, '|')
      }).catch(error => {
        console.log(error)
      })
    },
    popupClose: function () {
      this.$emit('fileUploadClose')
      // this.$parent.hideFileUpload()
      this.files = []
    },
    filesUpload: function () {
      const maxSize = 10 * 1024 * 1024
      let fileSize = 0
      let uploadFiles = this.$refs.files.files
      const conversion = byte => {
        return byte / 1048576
      }
      for (let i = 0; i < uploadFiles.length; i++) {
        let extention = uploadFiles[i].name.substring(uploadFiles[i].name.lastIndexOf('.') + 1)
        fileSize = fileSize + uploadFiles[i].size
        if (fileSize > maxSize) {
          this.$util.esmessage(this, 'warn', this.labels.fileSizeMessage)
        } else if (!this.extentions.includes(extention.toUpperCase())) {
        // } else if (!/\.(gif|jpg|jpeg|png|txt|xls|xlsx|ppt|pptx|docx|csv)$/i.test(uploadFiles[i].name)) {
          this.$util.esmessage(this, 'warn', this.labels.contentTypeMessage)
        } else {
          this.files.push(uploadFiles[i])
          const fileData = {}
          fileData['id'] = i + 1
          fileData['fileName'] = uploadFiles[i].name
          fileData['fileSize'] = conversion(uploadFiles[i].size).toFixed(3) + ' MB'
          this.fileGrid.data.push(fileData)
        }
      }
    },
    delFile: function (index) {
      this.fileGrid.data.splice(index, 1)
      this.files.splice(index, 1)
    },
    submitFiles: function () {
      if (this.uploadDescription === '') {
        this.$util.esmessage(this, 'warn', this.labels.descriptionMessage)
        return
      }
      if (!this.files.length) {
        this.$util.esmessage(this, 'warn', this.labels.fileMessage)
        return
      }
      let formData = new FormData()
      formData.append('description', this.uploadDescription)
      formData.append('ownerTable', this.ownertable)
      formData.append('ownerId', this.ownerid)
      for (let i = 0; i < this.fileGrid.data.length; i++) {
        formData.append('files', this.files[i], this.files[i].name)
      }
      this.$http.post('/episode/api/common/attachment/upload',
        formData,
        {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }
      ).then(res => {
        eventBus.$emit('fileListAdd', res.data)
        this.popupClose()
        this.$util.esmessage(this, 'check', this.labels.saveMessage)
        this.fileGrid.data = []
        this.files = []
      }).catch(error => {
        this.popupClose()
        this.$util.esmessage(this, 'warn', this.labels.failMessage)
        console.log(error)
      })
    }
  },

  created () {
    this.initialize()
  }
}
</script>
