(ns qbits.thorn.validators)

;; Hostnames with special/reserved meaning.
(def special-hostnames
  #{"autoconfig"    ;; Thunderbird autoconfig
    "autodiscover"  ;; MS Outlook/Exchange autoconfig
    "broadcasthost" ;; Network broadcast hostname
    "isatap"        ;; IPv6 tunnel autodiscovery
    "localdomain"   ;; Loopback
    "localhost"     ;; Loopback
    "wpad"          ;; Proxy autodiscovery
    })

(def protocol-hostnames
  #{"ftp" "imap" "mail" "news" "pop" "pop3" "smtp" "usenet" "uucp" "webmail"
    "www"})

(def ca-addresses
  #{"admin" "administrator" "hostmaster" "info" "is" "it" "mis" "postmaster"
    "root" "ssladmin" "ssladministrator" "sslwebmaster" "sysadmin" "webmaster"})

(def rfc-2142
  #{"abuse"
    "marketing"
    "noc"
    "sales"
    "security"
    "support"})

(def noreply-addresses
  #{"mailer-daemon" "nobody" "noreply" "no-reply"})

(def sensitive-filenames
  #{"clientaccesspolicy.xml" "crossdomain.xml" "favicon.ico" "humans.txt"
    "keybase.txt" "robots.txt" ".htaccess" ".htpasswd"})

(def other-sensitive-names
  #{ "account" "accounts" "blog" "buy" "clients" "contact" "contactus"
    "contact-us" "copyright" "dashboard" "doc" "docs" "download" "downloads"
    "enquiry" "faq" "help" "inquiry" "license" "login" "logout" "me" "myaccount"
    "payments" "plans" "portfolio" "preferences" "pricing" "privacy" "profile"
    "register" "secure" "settings" "signin" "signup" "ssl" "status"  "subscribe"
    "terms" "tos" "user" "users" "weblog" "work"})

(def default-reserved-names
  (set (concat ca-addresses rfc-2142 noreply-addresses
               sensitive-filenames other-sensitive-names)))
