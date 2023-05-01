-- API COMMON

-- API LOCK
drop table if exists t_lock;
create table if not exists t_lock (
    name text
  , se text
  , lock_dt datetime
  , unlock_dt datetime
  , primary key(name)
);





-- AWX API

-- Organization
drop table if exists t_awx_organization;
create table t_awx_organization (
    id text
  , name text
  , description text
  , se text
  , update_dt datetime
  , primary key(id)
);

-- Credential Type
drop table if exists t_awx_credential_type;
create table t_awx_credential_type (
    id text
  , name text
  , description text
  , kind text
  , namespace text
  , se text
  , update_dt datetime
  , primary key(id)
);

-- Credential
drop table if exists t_awx_credential;
create table t_awx_credential (
    id text
  , name text
  , description text
  , organization_id text
  , credential_type_id text
  , se text
  , update_dt datetime
  , primary key(id)
);

-- Inventory
drop table if exists t_awx_inventory;
create table t_awx_inventory (
    id text
  , name text
  , description text
  , organization_id text
  , host_count text
  , group_count text
  , inventory_source_count text
  , variables text
  , se text
  , update_dt datetime
  , primary key(id)
);

-- Inventory Source
drop table if exists t_awx_inventory_source;
create table t_awx_inventory_source (
    id text
  , name text
  , description text
  , inventory_id text
  , source text
  , source_path text
  , source_project_id text
  , status text
  , se text
  , update_dt datetime
  , primary key(id)
);


-- Group
drop table if exists t_awx_group;
create table t_awx_group (
    id text
  , name text
  , description text
  , inventory_id text
  , variables text
  , se text
  , update_dt datetime
  , primary key(id)
);

-- Host
drop table if exists t_awx_host;
create table t_awx_host (
    id text
  , name text
  , description text
  , inventory_id text
  , enabled text
  , variables text
  , se text
  , update_dt datetime
  , primary key(id)
);

-- Host All Group
drop table if exists t_awx_host_all_group;
create table t_awx_host_all_group (
    host_id text
  , group_id text
  , inventory_id text
  , se text
  , update_dt datetime
  , primary key(host_id,group_id)
);

-- Project
drop table if exists t_awx_project;
create table t_awx_project (
    id text
  , name text
  , description text
  , organization_id text
  , local_path text
  , scm_type text
  , scm_url text
  , se text
  , update_dt datetime
  , primary key(id)
);


-- Job Template
drop table if exists t_awx_job_template;
create table t_awx_job_template (
    id text
  , name text
  , description text
  , job_type text
  , organization_id text
  , inventory_id text
  , project_id text
  , playbook text
  , verbosity text
  , extra_vars text
  , se text
  , update_dt datetime
  , primary key(id)
);


-- Workflow Job Template
drop table if exists t_awx_workflow_job_template;
create table t_awx_workflow_job_template (
    id text
  , name text
  , description text
  , organization_id text
  , inventory_id text
  , extra_vars text
  , se text
  , update_dt datetime
  , primary key(id)
);



