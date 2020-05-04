package com.wzy.library;

public abstract class ParameterHandler {
    abstract void apply(RequestBuilder builder, String value);

    public static class Query extends ParameterHandler {

        //参数名
        private String name;
        public Query(String name) {
            if (name.isEmpty()) {
                throw new NullPointerException("name is empty");
            }
            this.name = name;
        }

        @Override
        void apply(RequestBuilder builder, String value) {
          if (value == null) {
              return;
          }

          builder.addQueryParam(name, value);
        }
    }

    public static class Field extends ParameterHandler{
        private String name;

        public Field(String name) {
            if (name.isEmpty()) {
                throw new NullPointerException("name is empty");
            }
            this.name = name;
        }

        @Override
        void apply(RequestBuilder builder, String value) {
           if (value == null) {
               builder.addFormField(name, value);
           }
        }
    }
}
