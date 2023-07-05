package com.hugqq.config;

@Slf4j
@Component
public class DataSync implements ITableChangeHandler {

    @Resource
    private DataSource dataSource;

    @Override
    public void delete(List<CanalEntry.Column> beforeList, String db, String table) {


        StringBuffer sql = new StringBuffer("delete from " + table + " where ");
        for (CanalEntry.Column column : beforeList) {
            if (column.getIsKey()) {
                sql.append(column.getName() + "=" + column.getValue());
                break;
            }
        }

        this.execute(sql.toString());
    }

    @Override
    public void insert(List<CanalEntry.Column> afterList, String db, String table) {

        StringBuffer sql = new StringBuffer("insert into " + table + " (");
        for (int i = 0; i < afterList.size(); i++) {
            sql.append(afterList.get(i).getName());
            if (i != afterList.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(") VALUES (");
        for (int i = 0; i < afterList.size(); i++) {
            sql.append("'" + afterList.get(i).getValue() + "'");
            if (i != afterList.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(")");
        this.execute(sql.toString());

    }

    @Override
    public void update(List<CanalEntry.Column> beforeList, List<CanalEntry.Column> afterList, String db, String table) {
        StringBuffer sql = new StringBuffer("update " + table + " set ");

        for (int i = 0; i < afterList.size(); i++) {
            sql.append(" " + afterList.get(i).getName()
                    + " = '" + afterList.get(i).getValue() + "'");
            if (i != afterList.size() - 1) {
                sql.append(",");
            }
        }

        sql.append(" where ");
        for (CanalEntry.Column column : beforeList) {
            if (column.getIsKey()) {
                sql.append(column.getName() + "=" + column.getValue());
                break;
            }
        }
        this.execute(sql.toString());
    }

    public void execute(String sql) {
        Connection con = null;
        try {
            if(null == sql) return;
            con = dataSource.getConnection();
            QueryRunner qr = new QueryRunner();
            qr.execute(con, sql, new ArrayListHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(con);
        }
    }
}
