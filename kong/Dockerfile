FROM mashape/kong
MAINTAINER Luis Duarte, lduarte@luisduarte.net

ENV CONSUL_TEMPLATE_VERSION 0.16.0

RUN yum install -y bind-utils unzip && \
    yum clean all

RUN cd /tmp && \
    wget https://releases.hashicorp.com/consul-template/$CONSUL_TEMPLATE_VERSION/consul-template_0.16.0_linux_amd64.zip && \
    unzip consul-template_0.16.0_linux_amd64.zip && \
    chmod a+x consul-template && \
    mv consul-template /usr/bin/consul-template

COPY docker-entrypoint.sh /docker-entrypoint.sh
COPY dnsmasq.conf /etc/dnsmasq.conf
COPY templates/etc/hosts.consul.ctmpl /root/hosts.consul.ctmpl

ENTRYPOINT ["/docker-entrypoint.sh"]

EXPOSE 8000 8443 8001 7946
CMD ["kong", "start", "-vv"]