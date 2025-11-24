#!/bin/bash

# Nacos配置中心导入脚本
# 用于将项目中的配置文件导入到Nacos配置中心

# 默认配置
NACOS_SERVER="localhost"
NACOS_PORT="8848"
NACOS_USERNAME="nacos"
NACOS_PASSWORD="nacos"
DEFAULT_GROUP="DEFAULT_GROUP"
NAMESPACE="public"

# 解析命令行参数
while [[ $# -gt 0 ]]; do
  case "$1" in
    --server)
      NACOS_SERVER="$2"
      shift 2
      ;;
    --port)
      NACOS_PORT="$2"
      shift 2
      ;;
    --username)
      NACOS_USERNAME="$2"
      shift 2
      ;;
    --password)
      NACOS_PASSWORD="$2"
      shift 2
      ;;
    --namespace)
      NAMESPACE="$2"
      shift 2
      ;;
    --group)
      DEFAULT_GROUP="$2"
      shift 2
      ;;
    --help|-h)
      echo "用法: $0 [选项...] [all|service_name]"
      echo "选项:"
      echo "  --server <host>      Nacos服务器地址 (默认: localhost)"
      echo "  --port <port>        Nacos服务器端口 (默认: 8848)"
      echo "  --username <user>    Nacos登录用户名 (默认: nacos)"
      echo "  --password <pwd>     Nacos登录密码 (默认: nacos)"
      echo "  --namespace <ns>     Nacos命名空间ID (默认: public)"
      echo "  --group <group>      配置分组 (默认: DEFAULT_GROUP)"
      echo "  --help|-h            显示此帮助信息"
      exit 0
      ;;
    *)
      break
      ;;
  esac
done

# 配置Nacos URL
NACOS_URL="http://${NACOS_SERVER}:${NACOS_PORT}/nacos/v1/cs/configs"

# 配置文件目录
CONFIG_DIR="$(cd "$(dirname "$0")" && pwd)"

# 检查curl命令是否存在
if ! command -v curl &> /dev/null; then
  echo "❌ 错误: 未找到curl命令，请先安装curl"
  exit 1
fi

# 检查Nacos服务器连接
echo "正在检查Nacos服务器连接: ${NACOS_URL}"
if ! curl -s -o /dev/null -w "%{http_code}" "http://${NACOS_SERVER}:${NACOS_PORT}" | grep -q "200"; then
  echo "❌ 警告: 无法连接到Nacos服务器 ${NACOS_SERVER}:${NACOS_PORT}"
  echo "请确认Nacos服务器已启动并可访问"
  read -p "是否继续执行？(y/n): " -n 1 -r
  echo
  if [[ ! $REPLY =~ ^[Yy]$ ]]; then
    exit 1
  fi
fi

echo "Nacos配置导入脚本开始执行..."
echo "配置文件目录: ${CONFIG_DIR}"
echo "Nacos服务器: ${NACOS_URL}"
echo "默认分组: ${DEFAULT_GROUP}"
echo "命名空间: ${NAMESPACE}"
echo "==================================="

# 导入单个配置文件的函数
# 参数1: 服务名
# 参数2: 环境
# 参数3: 配置文件路径
import_config() {
  local service_name=$1
  local env=$2
  local config_file=$3
  local data_id="${service_name}-${env}.yml"
  local group="${DEFAULT_GROUP}"
  
  # 读取配置文件内容
  local content=$(cat "${config_file}")
  
  echo "正在导入配置: ${data_id} (${group})"
  
  # 使用curl调用Nacos API导入配置（不使用namespaceId，使用默认命名空间）
  echo "Debug: curl -X POST ${NACOS_URL}"
  response=$(curl -v -X POST "${NACOS_URL}" \
    -d "dataId=${data_id}" \
    -d "group=${group}" \
    --data-urlencode "content=${content}")
  
  if [[ "${response}" == "true" ]]; then
    echo "✅ 配置导入成功: ${data_id}"
    return 0
  else
    echo "❌ 配置导入失败: ${data_id}"
    echo "  服务器响应: ${response}"
    echo "  检查Nacos服务器状态、网络连接和认证信息"
    return 1
  fi
}

# 批量导入指定服务的所有环境配置
# 参数1: 服务名
import_service_configs() {
  local service_name=$1
  echo "开始导入${service_name}服务的所有环境配置..."
  
  # 查找该服务的所有环境配置文件
  local config_files=($(find "${CONFIG_DIR}" -name "${service_name}-*.yml"))
  
  if [ ${#config_files[@]} -eq 0 ]; then
    echo "❌ 未找到${service_name}服务的配置文件"
    return 1
  fi
  
  # 遍历并导入每个配置文件
  local success_count=0
  local fail_count=0
  
  for config_file in "${config_files[@]}"; do
    # 从文件名提取环境信息
    local filename=$(basename "${config_file}")
    local env=$(echo "${filename}" | sed -E "s/${service_name}-(.*)\.yml/\1/")
    
    if import_config "${service_name}" "${env}" "${config_file}"; then
      success_count=$((success_count + 1))
    else
      fail_count=$((fail_count + 1))
    fi
  done
  
  echo "${service_name}服务配置导入完成: 成功${success_count}个, 失败${fail_count}个"
  echo "==================================="
}

# 导入所有服务的配置
import_all_configs() {
  echo "开始导入所有服务的配置..."
  
  # 获取所有唯一的服务名
  local services=()
  local config_files=($(find "${CONFIG_DIR}" -name "*.yml"))
  
  for config_file in "${config_files[@]}"; do
    local filename=$(basename "${config_file}")
    local service_name=$(echo "${filename}" | sed -E "s/(.*)-(dev|test|prod)\.yml/\1/")
    
    # 检查服务名是否已添加
    if [[ ! " ${services[*]} " =~ " ${service_name} " ]]; then
      services+=(${service_name})
    fi
  done
  
  # 导入每个服务的配置
  for service in "${services[@]}"; do
    import_service_configs "${service}"
  done
}

# 主函数
main() {
  local target_service=$1
  
  echo "Nacos配置导入工具"
  echo ""
  
  if [ -z "$target_service" ]; then
    echo "请指定要导入的服务名称或使用'all'导入所有服务"
    echo "运行 '$0 --help' 查看详细用法"
    exit 1
  fi
  
  # 记录开始时间
  local start_time=$(date +%s)
  
  if [ "$target_service" == "all" ]; then
    import_all_configs
  else
    import_service_configs "$target_service"
  fi
  
  # 计算执行时间
  local end_time=$(date +%s)
  local duration=$((end_time - start_time))
  
  echo "配置导入脚本执行完成! 耗时: ${duration}秒"
  echo "提示: 如果导入失败，请检查以下事项:"
  echo "  1. Nacos服务器是否正常运行"
  echo "  2. Nacos服务器地址和端口是否正确"
  echo "  3. 登录用户名和密码是否正确"
  echo "  4. 网络连接是否畅通"
}

# 执行主函数
main "$@"