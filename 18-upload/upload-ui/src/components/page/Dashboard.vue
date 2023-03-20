<template>
  <div>
    <el-row :gutter="20">
      <el-col>
        <el-card shadow="hover" style="height:403px;">
          <div slot="header" class="clearfix">
            <span>计划清单</span>
          </div>
          <el-table :show-header="false" :data="todoList" style="width:100%;">
            <el-table-column width="40">
              <template slot-scope="scope">
                <el-checkbox v-model="scope.row.status"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column>
              <template slot-scope="scope">
                <div
                    class="todo-item"
                    :class="{'todo-item-del': scope.row.status}"
                >{{ scope.row.title }}
                </div>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Schart from 'vue-schart';

export default {
  name: 'dashboard',
  data() {
    return {
      name: localStorage.getItem('ms_username'),
      todoList: [
        {
          title: '列表的分页实现',
          status: true
        },
        {
          title: '快速上传',
          status: true
        },
      ],
      data: [
      ]
    };
  },
  components: {
    Schart
  },
  computed: {

  },
  methods: {

  }
};
</script>


<style scoped>
.grid-con-1 .grid-con-icon {
  background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
  background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
  background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
  color: rgb(242, 94, 67);
}

.user-info-cont div:first-child {
  font-size: 30px;
  color: #222;
}


.user-info-list span {
  margin-left: 70px;
}


.todo-item {
  font-size: 14px;
}

.todo-item-del {
  text-decoration: line-through;
  color: #999;
}

</style>
