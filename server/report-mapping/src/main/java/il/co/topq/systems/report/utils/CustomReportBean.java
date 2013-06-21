//package il.co.topq.systems.report.utils;
//
//import il.co.topq.systems.report.common.model.CustomReport;
//import il.co.topq.systems.report.common.model.ReportProperty;
//import il.co.topq.systems.report.common.obj.Chunk;
//import il.co.topq.systems.report.common.obj.PropertyValuesWrapper;
//import il.co.topq.systems.report.common.obj.TimeRange;
//import org.hibernate.Query;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//public class CustomReportBean<P extends ReportProperty<P>, R extends CustomReport<P>> {
//
//    private EntityManager entityManager;
//
//    private EntityBean<R> entityBean;
//
//    private PropertyBean<P> propertyBean;
//
//    private Class<R> reportClazz;
//
//    public CustomReportBean(Class<P> propertyClazz, Class<R> reportClazz) {
//        this.reportClazz = reportClazz;
//        propertyBean = new PropertyBean<P>(propertyClazz);
//        entityBean = new EntityBean<R>(reportClazz);
//        entityManager = new EntityManager();
//    }
//
//    public R createCustomReport(R customReport) {
//        bindPropertiesWithCustomReport(customReport);
//        entityBean.createObject(customReport);
//        return customReport;
//    }
//
//
//    public void updateCustomReport(R customReport) {
//        bindPropertiesWithCustomReport(customReport);
//        entityBean.updateObject(customReport);
//    }
//
//
//    public Long countCustomReport() {
//        return entityBean.count();
//    }
//
//    public void bindPropertiesWithCustomReport(R customReport) {
//        Set<P> targetProperties = new HashSet<P>();
//        Set<P> properties = customReport.getProperties();
//        for (P property : properties) {
//            P prototype = property.getKeyPrototype();
//            targetProperties.add(propertyBean.createProperty(prototype));
//        }
//        customReport.setProperties(targetProperties);
//    }
//
//
//    public List<PropertyValuesWrapper> getAllPropertiesValues(Set<P> customReportPropertiesList) {
//        List<PropertyValuesWrapper> propertyValuesWrapperList = new ArrayList<PropertyValuesWrapper>();
//        for (P customReportProperty : customReportPropertiesList) {
//            String propertyKey = customReportProperty.getPropertyKey();
//            Set<String> propertyValueList = propertyBean.getPropertyValuesByKey(propertyKey);
//            propertyValuesWrapperList.add(new PropertyValuesWrapper(propertyKey, propertyValueList));
//        }
//        return propertyValuesWrapperList;
//    }
//
//    public R getCustomReport(Integer scenarioCustomReportID) {
//        return entityBean.getObject(scenarioCustomReportID);
//    }
//
//    public List<PropertyValuesWrapper> getCustomReportPropertyValues(Integer customReportId) {
//        CustomReport<P> customReport = getCustomReport(customReportId);
//        Set<P> customReportPropList = customReport.getProperties();
//        return getAllPropertiesValues(customReportPropList);
//    }
//
//    public void deleteCustomReport(final Integer id) {
//        entityBean.deleteObject(id);
//    }
//
//    @SuppressWarnings(value = "unchecked")
//    public List<R> getCustomReport(Chunk chunk, TimeRange timeRange) {
//        entityManager.openTransaction();
//        try {
//            StringBuilder sb = new StringBuilder();
//            sb.append("from ").append(reportClazz.getSimpleName()).append(" as custom_report");
//
//            if (timeRange != null) {
//                String timeRangeStr = entityManager.createTimeRangeRestrictionQuery(timeRange, "dateOfCreation");
//                if (timeRangeStr != null) {
//                    sb.append(" where ").append(timeRangeStr);
//                }
//            }
//            sb.append(" order by dateOfCreation desc");
//            Query query = entityManager.getSession().createQuery(sb.toString());
//            entityManager.setQueryResultSize(query, chunk);
//            return query.list();
//        } finally {
//            entityManager.closeTransaction();
//        }
//    }
//
//    /**
//     * @param timeRange used as property to filter scenarioCustomReport  -
//     * @return will return a the size of this query, this method will be used by
//     *         UI to build the pages according the result's size; Null in case
//     *         of error;
//     */
//    public int getSizeOfCustomReportByFilter(TimeRange timeRange) {
//        entityManager.openTransaction();
//        try {
//            StringBuilder sb = new StringBuilder();
//            sb.append("select count(*) from ").append(reportClazz.getName()).append("  as custom_report");
//            if (timeRange != null) {
//                String timeRangeStr = entityManager.createTimeRangeRestrictionQuery(timeRange, "dateOfCreation");
//                if (timeRangeStr != null) {
//                    sb.append(" where ").append(timeRangeStr);
//                }
//            }
//            Query query = entityManager.getSession().createQuery(sb.toString());
//            Object size = query.uniqueResult();
//            return Integer.parseInt(size.toString());
//        } finally {
//            entityManager.closeTransaction();
//        }
//    }
// }
