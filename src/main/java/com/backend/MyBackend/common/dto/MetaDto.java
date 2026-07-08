package com.backend.MyBackend.common.dto;

public class MetaDto{
    private String environment;

    private MetaDto(MetaDtoBuilder builder){
        this.environment = builder.environment;
    }

    public String getEnvironment(){
        return environment;
    }

    public static class MetaDtoBuilder{

        private String environment;

        public MetaDtoBuilder environment(String environment){
            this.environment = environment;
            return this;
        }

        public MetaDto build(){
            return new MetaDto(this);
        }
    }
}
