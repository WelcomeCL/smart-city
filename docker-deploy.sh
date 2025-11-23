#!/bin/bash

# Smart City 微服务部署脚本
# 提供构建、启动、停止、重启等功能

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 项目根目录
PROJECT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# 配置文件目录
NACOS_CONFIGS_DIR="${PROJECT_DIR}/nacos-configs"

# 输出信息函数
echo_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

echo_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

echo_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

echo_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# 检查Docker和Docker Compose是否安装
check_dependencies() {
    echo_info "检查Docker和Docker Compose..."
    
    if ! command -v docker &> /dev/null; then
        echo_error "Docker 未安装，请先安装Docker"
        exit 1
    fi
    
    if ! command -v docker-compose &> /dev/null && ! command -v docker compose &> /dev/null; then
        echo_error "Docker Compose 未安装，请先安装Docker Compose"
        exit 1
    fi
    
    echo_success "Docker环境检查通过"
}

# 编译项目
compile_project() {
    echo_info "开始编译项目..."
    
    cd "${PROJECT_DIR}"
    
    if [ -f "pom.xml" ]; then
        if command -v mvn &> /dev/null; then
            mvn clean package -DskipTests
            echo_success "项目编译成功"
        else
            echo_error "Maven 未安装，无法编译项目"
            exit 1
        fi
    else
        echo_warning "未找到pom.xml，跳过编译步骤"
    fi
}

# 导入Nacos配置
import_nacos_configs() {
    echo_info "导入Nacos配置..."
    
    if [ -f "${NACOS_CONFIGS_DIR}/import_to_nacos.sh" ]; then
        cd "${NACOS_CONFIGS_DIR}"
        chmod +x import_to_nacos.sh
        
        # 先检查Nacos服务是否运行
        if docker ps | grep -q "nacos-server"; then
            # 使用脚本导入所有配置
            ./import_to_nacos.sh all
            echo_success "Nacos配置导入成功"
        else
            echo_warning "Nacos服务未运行，将在服务启动后手动导入配置"
        fi
    else
        echo_warning "未找到Nacos导入脚本，跳过配置导入"
    fi
}

# 构建Docker镜像
build_images() {
    echo_info "开始构建Docker镜像..."
    
    cd "${PROJECT_DIR}"
    
    # 检测docker compose命令
    if command -v docker compose &> /dev/null; then
        DOCKER_COMPOSE="docker compose"
    else
        DOCKER_COMPOSE="docker-compose"
    fi
    
    ${DOCKER_COMPOSE} build
    echo_success "Docker镜像构建成功"
}

# 启动服务
start_services() {
    echo_info "开始启动服务..."
    
    cd "${PROJECT_DIR}"
    
    # 检测docker compose命令
    if command -v docker compose &> /dev/null; then
        DOCKER_COMPOSE="docker compose"
    else
        DOCKER_COMPOSE="docker-compose"
    fi
    
    ${DOCKER_COMPOSE} up -d
    echo_success "服务启动成功"
    
    # 显示服务状态
    show_services_status
}

# 停止服务
stop_services() {
    echo_info "开始停止服务..."
    
    cd "${PROJECT_DIR}"
    
    # 检测docker compose命令
    if command -v docker compose &> /dev/null; then
        DOCKER_COMPOSE="docker compose"
    else
        DOCKER_COMPOSE="docker-compose"
    fi
    
    ${DOCKER_COMPOSE} down
    echo_success "服务停止成功"
}

# 重启服务
restart_services() {
    echo_info "开始重启服务..."
    
    stop_services
    start_services
}

# 查看服务状态
show_services_status() {
    echo_info "服务状态："
    
    cd "${PROJECT_DIR}"
    
    # 检测docker compose命令
    if command -v docker compose &> /dev/null; then
        DOCKER_COMPOSE="docker compose"
    else
        DOCKER_COMPOSE="docker-compose"
    fi
    
    ${DOCKER_COMPOSE} ps
}

# 查看服务日志
show_logs() {
    local service_name=$1
    
    cd "${PROJECT_DIR}"
    
    # 检测docker compose命令
    if command -v docker compose &> /dev/null; then
        DOCKER_COMPOSE="docker compose"
    else
        DOCKER_COMPOSE="docker-compose"
    fi
    
    if [ -n "${service_name}" ]; then
        echo_info "查看 ${service_name} 服务日志："
        ${DOCKER_COMPOSE} logs -f "${service_name}"
    else
        echo_info "查看所有服务日志："
        ${DOCKER_COMPOSE} logs -f
    fi
}

# 清理Docker资源
clean_resources() {
    echo_info "开始清理Docker资源..."
    
    # 停止并删除所有容器
    docker stop $(docker ps -aq) 2>/dev/null || true
    docker rm $(docker ps -aq) 2>/dev/null || true
    
    # 删除所有悬空镜像
    docker rmi $(docker images -f "dangling=true" -q) 2>/dev/null || true
    
    # 删除未使用的网络
    docker network prune -f
    
    echo_success "Docker资源清理完成"
}

# 显示帮助信息
show_help() {
    echo ""
    echo "Smart City 微服务部署脚本"
    echo "用法: $0 [选项]"
    echo ""
    echo "选项:"
    echo "  build           构建Docker镜像"
    echo "  start           启动所有服务"
    echo "  stop            停止所有服务"
    echo "  restart         重启所有服务"
    echo "  status          查看服务状态"
    echo "  logs [服务名]    查看服务日志，不指定服务名则查看所有日志"
    echo "  compile         编译项目"
    echo "  import-configs  导入Nacos配置"
    echo "  clean           清理Docker资源"
    echo "  full-deploy     完整部署流程（编译、构建、启动）"
    echo "  help            显示帮助信息"
    echo ""
}

# 完整部署流程
full_deploy() {
    echo_info "开始完整部署流程..."
    
    check_dependencies
    compile_project
    build_images
    start_services
    
    # 等待Nacos服务启动
    echo_info "等待Nacos服务启动..."
    sleep 30
    
    import_nacos_configs
    
    echo_success "完整部署流程完成！"
}

# 主函数
main() {
    if [ $# -eq 0 ]; then
        show_help
        exit 0
    fi
    
    case "$1" in
        build)
            check_dependencies
            build_images
            ;;
        start)
            check_dependencies
            start_services
            ;;
        stop)
            stop_services
            ;;
        restart)
            restart_services
            ;;
        status)
            show_services_status
            ;;
        logs)
            show_logs "$2"
            ;;
        compile)
            compile_project
            ;;
        import-configs)
            import_nacos_configs
            ;;
        clean)
            clean_resources
            ;;
        full-deploy)
            full_deploy
            ;;
        help)
            show_help
            ;;
        *)
            echo_error "未知选项: $1"
            show_help
            exit 1
            ;;
    esac
}

# 执行主函数
main "$@"